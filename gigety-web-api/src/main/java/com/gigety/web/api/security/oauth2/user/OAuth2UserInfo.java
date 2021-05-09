package com.gigety.web.api.security.oauth2.user;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class OAuth2UserInfo {
	protected Map<String, Object> attributes;
	
	public abstract String getId();
	public abstract String getName();
	public abstract String getEmail();
	public abstract String getImageUrl();
	
}
