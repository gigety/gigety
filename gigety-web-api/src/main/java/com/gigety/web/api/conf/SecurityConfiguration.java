package com.gigety.web.api.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gigety.web.api.security.JwtAuthenticationFilter;
import com.gigety.web.api.security.RestAutheticationEntryPoint;
import com.gigety.web.api.security.SecurityConstants;
import com.gigety.web.api.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.gigety.web.api.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.gigety.web.api.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.gigety.web.api.service.impl.OAuth2UserServiceImpl;
import com.gigety.web.api.service.impl.UserDetailsServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
	securedEnabled = true,
	jsr250Enabled = true,
	prePostEnabled = true
)
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	private final UserDetailsServiceImpl userDetailsServiceImpl;
	private final OAuth2UserServiceImpl oauth2UserServiceImpl;
	private final OAuth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;
	private final OAuth2AuthenticationFailureHandler oauth2AuthenticationFailureHandler;
	private final HttpCookieOAuth2AuthorizationRequestRepository cookieAuthRepo;
	private final RestAutheticationEntryPoint restAutheticationEntryPoint;

	public SecurityConfiguration(UserDetailsServiceImpl userDetailsServiceImpl, OAuth2UserServiceImpl oAuth2ServiceImpl,
			OAuth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler,
			OAuth2AuthenticationFailureHandler oauth2AuthenticationFailureHandler,
			HttpCookieOAuth2AuthorizationRequestRepository cookieAuthRepo,
			RestAutheticationEntryPoint restAutheticationEntryPoint) {
		super();
		this.userDetailsServiceImpl = userDetailsServiceImpl;
		this.oauth2UserServiceImpl = oAuth2ServiceImpl;
		this.oauth2AuthenticationSuccessHandler = oauth2AuthenticationSuccessHandler;
		this.oauth2AuthenticationFailureHandler = oauth2AuthenticationFailureHandler;
		this.cookieAuthRepo = cookieAuthRepo;
		this.restAutheticationEntryPoint = restAutheticationEntryPoint;
	}

	@Bean
	public JwtAuthenticationFilter tokenAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	
//	@Bean
//	public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
//		log.debug("Using stateless HttpCookieOAuth2AuthorizationRequestRepository for saving authorization requests");
//		return cookieAuthRepo;//new HttpCookieOAuth2AuthorizationRequestRepository();
//	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		//Overriding to add @Bean(BeanIds.AUTHENTICATION_MANAGER) to assure our authentication is available to context
		return super.authenticationManager();
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(restAutheticationEntryPoint)
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.formLogin().disable()
			.httpBasic().disable()
			.headers().frameOptions().sameOrigin() //To enable H2 Database
			.and()
			.authorizeRequests()
			.antMatchers(SecurityConstants.WEB_RESOURCES_URL).permitAll()
			.antMatchers(SecurityConstants.SIGN_UP_URL).permitAll()
			.antMatchers(SecurityConstants.AUTH_URLS).permitAll()
			.anyRequest().authenticated()
			.and()
			.oauth2Login()
				.authorizationEndpoint()
					.baseUri("/oauth2/authorize")
					.authorizationRequestRepository(cookieAuthRepo)
					.and()
				.redirectionEndpoint()
					.baseUri("/oauth2/callback/*")
					.and()
				.userInfoEndpoint()
					.userService(oauth2UserServiceImpl)
					.and()
				.successHandler(oauth2AuthenticationSuccessHandler)
				.failureHandler(oauth2AuthenticationFailureHandler);
		http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		super.configure(http);
	}
	
}
