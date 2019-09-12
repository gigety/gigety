package com.gigety.ur.conf;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.gigety.ur.util.GigConstants;
import com.gigety.ur.util.GigRoles;

import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity
@Configuration
@Slf4j
public class GigSecurityConfiguration extends WebSecurityConfigurerAdapter{

	private final UserDetailsService userDetailsService;
	private final DataSource dataSource;	
	
	public GigSecurityConfiguration(UserDetailsService userDetailsService, DataSource dataSource) {
		super();
		this.userDetailsService = userDetailsService;
		this.dataSource = dataSource;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		log.debug("ROLE :: {}",GigRoles.GIG_USER.toString());
		
		auth.authenticationProvider(daoAuthenticationProvider());
		auth.authenticationProvider(runAsAuthenticationProvider());
		log.debug("Configuring GLOBAL auth :: {}", auth);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
		auth.authenticationProvider(runAsAuthenticationProvider());
		log.debug("Configuring auth :: {}", auth);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
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
			//TODO: useSe ureTrue once https is configuredz
			//.useSecureCookie(true)
		.and()
		.logout().permitAll().logoutUrl("/logout")
		.and()
		.csrf().disable();
		// @formatter:on
	}

	@Bean
	public PasswordEncoder delegatingPasswordEncoder() {
		return new BCryptPasswordEncoder();
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
		authProvider.setPasswordEncoder(delegatingPasswordEncoder());
		return authProvider;
	}
	
}
