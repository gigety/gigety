package com.gigety.web.api.conf.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.Getter;

@ConfigurationProperties(prefix = "gig")
@Getter
public class GigAuthProperties {

	private final Auth auth = new Auth();
	private final OAuth2 oauth2 = new OAuth2();
	private final EndPoints endpoints = new EndPoints();
	
	@Data
	public final static class Auth {
		private String tokenSecret;
		private long tokenExpirationMsec;
	}
	
	@Data
	public final static class OAuth2 {
		private List<String> authorizedRedirectUris;
	}
	
	@Data
	private final static class EndPoints {
		private Web web;
	
	}
	
	@Data
	private final static class Web {
		private Cors cors;
	}
	
	@Data
	private final static class Cors {
		private List<String> allowedOrigins;
	}
}
