package com.gigety.web.api.security.oauth2.user;

import java.util.Map;

import com.gigety.web.api.exception.OAuth2AuthenticationProcessException;
import com.gigety.web.api.util.AuthProvider;

public class OAuth2UserInfoFactory {
	public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
		if (registrationId.equals(AuthProvider.google.toString())) {
			return new GoogleOAuth2UserInfo(attributes);
		} else if (registrationId.equals(AuthProvider.facebook.toString())) {
			return new FacebookOauth2UserInfo(attributes);
		} else {
			throw new OAuth2AuthenticationProcessException(
					String.format(" %s login is not yet supported - please do something", registrationId));
		}
	}
}
