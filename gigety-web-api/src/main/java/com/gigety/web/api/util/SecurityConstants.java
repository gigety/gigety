package com.gigety.web.api.util;

public class SecurityConstants {
	public static final String[] AUTH_URLS = {
			"/auth/**",
			"/oauth2/**"
	};
	//TODO: move public urls to a node server. 
	//THis is to become familiar with node 
	//THis also puts public traffic off the private server
	public static final String[] PUBLIC_URLS = {
			"/pnode/**"
	};
	public static final String SIGN_UP_URL = "/api/users/**";
	public static final String[] WEB_RESOURCES_URL = {
			"/",
			"/static/**",
			"/favicon.ico",
			"/**/*.png",
			"/**/*.png",
			"/**/*.png",
			"/**/*.png",
			"/**/*.png",
			"/**/*.png",
			"/**/*.png"
	};
	public static final String H2_CONSOLE_URL = "/h2-console";
	
	public static final String SECRET_KEY = "SecretKeyToGenJWTs";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_TOKEN = "Authorization";
	public static final long EXPIRATION_TIME = 5_000_000; //600 seconds
	
	 
}
