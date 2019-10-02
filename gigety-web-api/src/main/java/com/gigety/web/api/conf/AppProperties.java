package com.gigety.web.api.conf;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.Getter;

@ConfigurationProperties(prefix = "gig")
@Getter
public class AppProperties {

	private final Auth auth = new Auth();
	private final OAuth2 oauth2 = new OAuth2();
	
	@Data
	public final static class Auth {
		private String tokenSecret;
		private long tokenExpirationMsec;
	}
	
	@Data
	public final static class OAuth2 {
		private List<String> authorizationRedirectUris;
	}
}
