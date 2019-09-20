package com.gigety.ur.conf;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.gigety.ur.conf.filters.GigLogFilter;
import com.gigety.ur.security.provider.authentication.CustomAuthenticationProvider;
import com.gigety.ur.util.GigConstants;
import com.gigety.ur.util.GigRoles;

import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity
@Configuration
@Slf4j
public class GigSecurityConfiguration extends WebSecurityConfigurerAdapter{

	private final UserDetailsService userDetailsService;
	private final DataSource dataSource;	
	private final GigLogFilter gigLogFilter;
	private final CustomAuthenticationProvider customAuthenticationProvider;
	


	public GigSecurityConfiguration(UserDetailsService userDetailsService, DataSource dataSource,
			GigLogFilter gigLogFilter, CustomAuthenticationProvider customAuthenticationProvider) {
		super();
		this.userDetailsService = userDetailsService;
		this.dataSource = dataSource;
		this.gigLogFilter = gigLogFilter;
		this.customAuthenticationProvider = customAuthenticationProvider;
	}


	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		log.debug("ROLE :: {}",GigRoles.GIG_USER.toString());
		
		//CONFIGURE STORAGE - extra user is demonstration purpose only
		/*******************************************************************/
//		
//		auth.jdbcAuthentication()
//			.dataSource(dataSource)
//			
//			.withDefaultSchema()
//			.passwordEncoder(passwordEncoder())
//			.withUser("gig@gig.com")
//				.password(passwordEncoder().encode("gig"))
//				.roles("USER");
		
		//auth.inMemoryAuthentication().withUser("inmem@inmem.com").password("inmem").roles("USER");
		
		//CONFIGURE PROVIDERS
		/*******************************************************************/
		//either define provider manager and reconfigure any options of 
		//interest as follows:
//		
//		ProviderManager authManager = new ProviderManager(
//				Arrays.asList(customAuthenticationProvider,
//				daoAuthenticationProvider(), 
//				runAsAuthenticationProvider()));
//		authManager.setEraseCredentialsAfterAuthentication(false);
		
		//or simply add providers to existing providerManager like so:
		
		auth
			.authenticationProvider(daoAuthenticationProvider())
			.authenticationProvider(runAsAuthenticationProvider());
			//.authenticationProvider(customAuthenticationProvider);	
		
		
		log.debug("Configuring GLOBAL auth :: {}", auth);
	}


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {	
		
		//TODO: experimenting with custom providers
		//auth.authenticationProvider(customAuthenticationProvider);
		auth.authenticationProvider(daoAuthenticationProvider())
			.authenticationProvider(runAsAuthenticationProvider());
		
		log.debug("Configuring auth :: {}", auth);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
		//TODO: Experimenting custom filters
		.addFilterBefore(gigLogFilter, AnonymousAuthenticationFilter.class)
		.headers().frameOptions().sameOrigin() // for h2 in mem db during dev 
		.and()
		.authorizeRequests()
			.antMatchers(
					"/signup",
					"/user/register", 
					"/reg/register", 
					"/forgotpw", 
					"/reg/resetpw",
					"/reg/updatepw",
					"/reg/savepw",
					"/reg/confirm-reg/**",
					"/registrationConfirm*", 		
					"/js/**",
					"/h2-console/**" ) //h2-console for early dev stage only
			.permitAll()
			.anyRequest().authenticated()
			
		.and()
		.formLogin()
			.loginPage("/login").permitAll()
			.loginProcessingUrl("/doLogin")
			
		.and()
		.rememberMe().tokenRepository(persistentTokenRepository())
			//.key("gigetyKey")
			//.tokenValiditySeconds(604800)
			//.rememberMeCookieName("sticky-cookie")
			//.rememberMeParameter("gig-rem-user")
			//TODO: useSeureTrue once https is configuredz
			//.useSecureCookie(true)
		.and()
		.logout().permitAll().logoutUrl("/logout")
		.and()
		//following is for getting activeUsers
		.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry()).and().sessionFixation().none()
		.and()
		.csrf().disable();
		// @formatter:on
	}

	/**
	 * Used to get active users
	 * @return
	 */
	@Bean 
	SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		log.debug("Defining BCryptPasswordEncoder {} ", encoder);
		return encoder;
	}
	
	@Bean PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		return repo;	
	}
	
	@Bean
	public AuthenticationProvider runAsAuthenticationProvider() {
		RunAsImplAuthenticationProvider authProvider = new RunAsImplAuthenticationProvider();
		authProvider.setKey(GigConstants.RUN_AS_KEY);
		return authProvider;
	}
	
	@Bean
	public AuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
}
