package com.gigety.ur.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gigety.ur.util.GigRoles;

import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity
@Configuration
@Slf4j
public class GigSecurityConf extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		log.debug("ROLE :: {}",GigRoles.GIG_USER.toString());
		auth.userDetailsService(userDetailsService).passwordEncoder(delegatingPasswordEncoder());
	}
//	
//	 @Override
//	   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//	      auth.userDetailsService(userDetailsService)
////	              .passwordEncoder(new ShaPasswordEncoder(encodingStrength));
//	      			.passwordEncoder(delegatingPasswordEncoder());
//	   }
	

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
					"/registrationConfirm*", 		
					"/reg/confirm-reg/**",
					"/h2-console/**" ) //h2-console for early dev stage only
			.permitAll()
			.anyRequest().authenticated()
			
		.and()
		.formLogin()
			.loginPage("/login").permitAll()
			.loginProcessingUrl("/doLogin")
			
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
}
