package com.gigety.ur.security.provider.authentication;

import java.util.Arrays;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.gigety.ur.security.GigUserDetailServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final GigUserDetailServiceImpl userDetailsService;

	public CustomAuthenticationProvider(GigUserDetailServiceImpl userDetailsService) {
		super();
		this.userDetailsService = userDetailsService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.debug("Performing custom authentication on {}", authentication);
		final String name = authentication.getName();
		if (!supportsAuthenticaation(authentication)) {
			return null;
		}
		if (doAuthenticationAgainstThirdPartySystem()) {
			final UserDetails user = userDetailsService.loadUserByUsername(name);
			return new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(),
					Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
		} else {
			throw new BadCredentialsException("Authentication against thrid party is a complete failure, pathetic");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);

	}

	private boolean doAuthenticationAgainstThirdPartySystem() {
		// currently this is for demonstration purpose only
		// in real world we would look up and authenticate user via whatever
		// i assume such as LDAP, DB, REST request to other system.
		// for demonstration purpose we simply return true
		// saying yes this is so and so...
		return true;
	}

	private boolean supportsAuthenticaation(Authentication authentication) {
		// Here we can check if we should perform authentication
		return true;
	}
}
