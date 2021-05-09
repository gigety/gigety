package com.gigety.web.api.security.oauth2.user;

import java.util.Map;

import com.gigety.web.api.exception.OAuth2AuthenticationProcessException;
import com.gigety.web.api.util.AuthProviderConstants;

public class OAuth2UserInfoFactory {
	public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
		if(registrationId.equals(AuthProviderConstants.SAMO)) {
			return new SamoOauth2UserInfo(attributes);
		} else if (registrationId.equals(AuthProviderConstants.GOOGLE)) {
			return new GoogleOAuth2UserInfo(attributes);
		} else if (registrationId.equals(AuthProviderConstants.FACEBOOK)) {
			return new FacebookOauth2UserInfo(attributes);
		} else {
			throw new OAuth2AuthenticationProcessException(
					String.format(" %s login is not yet supported - please do something", registrationId));
		}
	}
}
