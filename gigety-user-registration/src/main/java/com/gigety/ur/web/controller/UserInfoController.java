package com.gigety.ur.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserInfoController {

	@RequestMapping(method = RequestMethod.GET, value = "/users/info")
	@ResponseBody
	public Map<String, Object> getExtraInfo(OAuth2Authentication auth){
		//TODO: add ids expected in client app
		Map<String, Object> map = new HashMap<>();
		map.put("user_name", auth.getName());
		map.put("email", auth.getName());
		map.put("id", auth.getName());
		map.put("name", auth.getName());
		return map;//Collections.singletonMap("user_name", auth.getName());
	}
}
