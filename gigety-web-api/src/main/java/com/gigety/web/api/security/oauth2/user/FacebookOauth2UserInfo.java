package com.gigety.web.api.security.oauth2.user;

import java.util.Map;

public class FacebookOauth2UserInfo extends OAuth2UserInfo {

	public FacebookOauth2UserInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getId() {
		return (String) attributes.get("id");
	}

	@Override
	public String getName() {
		return (String) attributes.get("name");
	}

	@Override
	public String getEmail() {
		return (String) attributes.get("email");
	}

	@Override
	public String getImageUrl() {
		if(attributes.containsKey("picture")) {
			@SuppressWarnings("unchecked")
			Map<String, Object> picture = (Map<String, Object>) attributes.get("picture");
			if(picture.containsKey("data")) {
				@SuppressWarnings("unchecked")
				Map<String, Object> data = (Map<String, Object>) picture.get("data");
				if(data.containsKey("url")) {
					return (String) data.get("url");
				}
				
			}
		}
		return null;
	}

}
