package com.gigety.web.api.conf;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.gigety.web.api.security.RestAuthenticationEntryPoint;
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

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	@Autowired
	private OAuth2UserServiceImpl oauth2UserServiceImpl;
	@Autowired
	private OAuth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;
	@Autowired
	private OAuth2AuthenticationFailureHandler oauth2AuthenticationFailureHandler;
	@Autowired
	private HttpCookieOAuth2AuthorizationRequestRepository cookieAuthRepo;
	//private final RestAuthenticationEntryPoint restAutheticationEntryPoint;



	@Bean
	public JwtAuthenticationFilter tokenAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	


	@Bean
	public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
		log.debug("Using stateless HttpCookieOAuth2AuthorizationRequestRepository for saving authorization requests");
		return new HttpCookieOAuth2AuthorizationRequestRepository();
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
        .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
        .csrf()
            .disable()
        .formLogin()
            .disable()
        .httpBasic()
            .disable()
        .exceptionHandling()
            .authenticationEntryPoint(new RestAuthenticationEntryPoint())
            .and()
        .authorizeRequests()
            .antMatchers("/",
                "/error",
                "/favicon.ico",
                "/**/*.png",
                "/**/*.gif",
                "/**/*.svg",
                "/**/*.jpg",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js")
                .permitAll()
            .antMatchers("/auth/**", "/oauth2/**")
                .permitAll()
            .anyRequest()
                .authenticated()
            .and()
        .oauth2Login()
            .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                .and()
            .redirectionEndpoint()
                .baseUri("/oauth2/callback/*")
                .and()
            .userInfoEndpoint()
                .userService(oauth2UserServiceImpl)
                .and()
            .successHandler(oauth2AuthenticationSuccessHandler)
            .failureHandler(oauth2AuthenticationFailureHandler);

// Add our custom Token based authentication filter
http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
}
