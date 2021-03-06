package com.gigety.web.api.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.gigety.web.api.conf.properties.GigAuthProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT Token provider. Used to generate and validate tokens.
 */
@Component
@Slf4j
@AllArgsConstructor
public class JwtTokenProvider {

	private final GigAuthProperties gigAuthProperties;

	public String createToken(Authentication authentication) {
		UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
		Date now = new Date();
		Date expireDate = new Date(now.getTime() + gigAuthProperties.getAuth().getTokenExpirationMsec());
		
		String userid = String.valueOf(user.getId());
		
		Map<String, Object> claims = new HashMap<>();
		
		claims.put("gig-user-id", userid);
		claims.put("whatever", "itmaybe");
		String ret = Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS512, gigAuthProperties.getAuth().getTokenSecret())
				.compact();
		log.debug("Generating token :: {}", ret);
		return ret;
	}
	
	/**
	 * Validate the token
	 * @param token
	 * @return
	 */
	public boolean validateToken(String token) {

			try {
				//Validate token is from user? Should we PKCE?
				Claims claims  = Jwts.parser().setSigningKey(gigAuthProperties.getAuth().getTokenSecret()).parseClaimsJws(token).getBody();
				return claims.getExpiration().after(new Date());
			} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
					| IllegalArgumentException e) {
				log.error("Error validating token: {}", e.getMessage());
			}

		return false;
	}
	
	/**
	 * Get user id from the claim included in JWT
	 * @param token
	 * @return
	 */
	public Long getUserIdFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(gigAuthProperties.getAuth().getTokenSecret()).parseClaimsJws(token).getBody();
		claims.values().forEach(claim -> log.debug("VALUE :: {}", claim));
		return Long.parseLong(String.valueOf(claims.get("gig-user-id")));
	}
}
