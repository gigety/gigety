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
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import com.gigety.web.api.security.JwtAuthenticationFilter;
import com.gigety.web.api.security.RestAuthenticationEntryPoint;
import com.gigety.web.api.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.gigety.web.api.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.gigety.web.api.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.gigety.web.api.service.impl.OAuth2UserServiceImpl;
import com.gigety.web.api.service.impl.UserDetailsServiceImpl;
import com.gigety.web.api.util.SecurityConstants;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
	securedEnabled = true,
	jsr250Enabled = true,
	prePostEnabled = true
)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	private final UserDetailsServiceImpl userDetailsServiceImpl;
	private final OAuth2UserServiceImpl oauth2UserServiceImpl;
	private final OAuth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;
	private final OAuth2AuthenticationFailureHandler oauth2AuthenticationFailureHandler;
	private final HttpCookieOAuth2AuthorizationRequestRepository cookieAuthRepo;
	private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	
	public SecurityConfiguration(UserDetailsServiceImpl userDetailsServiceImpl,
			OAuth2UserServiceImpl oauth2UserServiceImpl,
			OAuth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler,
			OAuth2AuthenticationFailureHandler oauth2AuthenticationFailureHandler,
			HttpCookieOAuth2AuthorizationRequestRepository cookieAuthRepo,
			RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
		super();
		this.userDetailsServiceImpl = userDetailsServiceImpl;
		this.oauth2UserServiceImpl = oauth2UserServiceImpl;
		this.oauth2AuthenticationSuccessHandler = oauth2AuthenticationSuccessHandler;
		this.oauth2AuthenticationFailureHandler = oauth2AuthenticationFailureHandler;
		this.cookieAuthRepo = cookieAuthRepo;
		this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
	}

	@Bean
	public JwtAuthenticationFilter tokenAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

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
		http
			.cors()
				.and()
			.csrf().disable()
			.formLogin().disable()
			.httpBasic().disable()
			.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
				.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
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
//            http.headers().addHeaderWriter(
//                    new StaticHeadersWriter("Access-Control-Allow-Origin", "*"));
	}
	
}
