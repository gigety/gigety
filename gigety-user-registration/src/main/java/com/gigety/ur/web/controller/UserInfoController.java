package com.gigety.ur.web.controller;

import java.util.Map;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserInfoController {

	@RequestMapping(method = RequestMethod.GET, value = "/users/info")
	@ResponseBody
	public Map<String, Object> getExtraInfo(OAuth2Authentication auth) {
		log.debug("GIGETY AUTH users/info request :: {}", auth);

		// TODO: add ids expected in client app
		//oauthDetail.getDetails contains additional attributes to map
		OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) oauthDetails.getDecodedDetails();
		map.put("user_name", auth.getName());
		map.put("email", auth.getName());
		map.put("id", auth.getName());
		map.put("name", auth.getName());
		return map;// Collections.singletonMap("user_name", auth.getName());
	}
}
