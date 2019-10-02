//package com.gigety.ur.conf;
//
//import java.util.List;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.access.AccessDecisionManager;
//import org.springframework.security.access.AccessDecisionVoter;
//import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
//import org.springframework.security.access.vote.AuthenticatedVoter;
//import org.springframework.security.access.vote.RoleVoter;
//import org.springframework.security.access.vote.UnanimousBased;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.session.SessionRegistry;
//import org.springframework.security.core.session.SessionRegistryImpl;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.web.access.expression.WebExpressionVoter;
//import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
//import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
//import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
//
//import com.gigety.ur.conf.filters.GigLogFilter;
//import com.gigety.ur.security.RealTimeLockVoter;
//import com.gigety.ur.util.GigConstants;
//import com.gigety.ur.util.GigRoles;
//import com.google.common.collect.Lists;
//
//import lombok.extern.slf4j.Slf4j;
//
//@EnableWebSecurity
//@Configuration
//@EnableResourceServer
//@Slf4j
//public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter{
//
//	private final UserDetailsService userDetailsService;
//	private final DataSource dataSource;	
//	private final GigLogFilter gigLogFilter;
//	private final RealTimeLockVoter realTimeLockVoter;
//	
//
//
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		log.debug("ROLE :: {}",GigRoles.GIG_USER.toString());
//		
//		auth
//			.authenticationProvider(daoAuthenticationProvider())
//			.authenticationProvider(runAsAuthenticationProvider());
//		
//		log.debug("Configuring GLOBAL auth :: {}", auth);
//	}
//
//
//	public ResourceServerConfiguration(UserDetailsService userDetailsService, DataSource dataSource,
//			GigLogFilter gigLogFilter, RealTimeLockVoter realTimeLockVoter) {
//		super();
//		this.userDetailsService = userDetailsService;
//		this.dataSource = dataSource;
//		this.gigLogFilter = gigLogFilter;
//		this.realTimeLockVoter = realTimeLockVoter;
//	}
//
//	
//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//		// @formatter:off
//		http.
//		//TODO: Experimenting custom filters
//		//.addFilterBefore(gigLogFilter, AnonymousAuthenticationFilter.class)
//		//.headers().frameOptions().sameOrigin() // for h2 in mem db during dev 
//		//.and()        http.
//        authorizeRequests().
//        // antMatchers("/oauth/token").permitAll().
//        anyRequest().authenticated().and().
//        sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
//        csrf().disable();
//		// @formatter:on
//	}
//
//	/**
//	 * Used to get active users
//	 * @return
//	 */
//	@Bean 
//	SessionRegistry sessionRegistry() {
//		return new SessionRegistryImpl();
//	}
//	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		log.debug("Defining BCryptPasswordEncoder {} ", encoder);
//		return encoder;
//	}
//	
//	@Bean PersistentTokenRepository persistentTokenRepository() {
//		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
//		repo.setDataSource(dataSource);
//		return repo;	
//	}
//	
//	@Bean
//	public AuthenticationProvider runAsAuthenticationProvider() {
//		RunAsImplAuthenticationProvider authProvider = new RunAsImplAuthenticationProvider();
//		authProvider.setKey(GigConstants.RUN_AS_KEY);
//		return authProvider;
//	}
//	
//	@Bean
//	public AuthenticationProvider daoAuthenticationProvider() {
//		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//		authProvider.setUserDetailsService(userDetailsService);
//		authProvider.setPasswordEncoder(passwordEncoder());
//		return authProvider;
//	}
//
//	@Bean
//	public AccessDecisionManager unanimous() {
//		List<AccessDecisionVoter<? extends Object>> decisionVoters = 
//				Lists.newArrayList(
//						new RoleVoter(),
//						new AuthenticatedVoter(), 
//						new WebExpressionVoter(), 
//						realTimeLockVoter);
//		return new UnanimousBased(decisionVoters);
//	}
//}
