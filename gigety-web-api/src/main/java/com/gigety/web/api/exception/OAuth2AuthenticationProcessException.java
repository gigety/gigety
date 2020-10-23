package com.gigety.web.api.exception;

import org.springframework.security.core.AuthenticationException;

public class OAuth2AuthenticationProcessException extends AuthenticationException {

	private static final long serialVersionUID = 5092428985164950052L;

	public OAuth2AuthenticationProcessException(String msg) {
		super(msg);
	}

	public OAuth2AuthenticationProcessException(String msg, Throwable t) {
		super(msg, t);
	}

}
