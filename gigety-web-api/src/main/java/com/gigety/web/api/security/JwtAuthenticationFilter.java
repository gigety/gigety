package com.gigety.web.api.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gigety.web.api.service.impl.UserDetailsServiceImpl;
import com.gigety.web.api.util.SecurityConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * Filter to handle tokens for security.
 * OncePerRequestFilter is extended to assist in Stateless RESTFull API
 * security.
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String jwt = parseTokenFromRequest(request);
			if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
				log.debug("Bearer token provided: {}", jwt);
				// Get user from token
				Long userid = jwtTokenProvider.getUserIdFromToken(jwt);

				// Get user with details from UserDetails Service
				UserDetails userDetails = userDetailsService.loadByUserId(userid);

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities()); 

				// Set details for the username pw authentication
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// Set the authentication for context of this request
				log.debug("Set COntextHolder Authentication :: {}", authentication);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				//response.addHeader("Access-Control-Allow-Origin", "*");
			}else {
				log.debug("No bearer token provided");
			}
		} catch (Exception e) {
			log.error(String.format("Exception in setting Authetication: %s", e.getMessage()));
			throw e;
		}
		filterChain.doFilter(request, response);
	}

	private String parseTokenFromRequest(HttpServletRequest request) {

		String token = request.getHeader(SecurityConstants.HEADER_TOKEN);
		
		if (token != null && token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			return token.substring(7, token.length());
		}
		return null;

	}

}
