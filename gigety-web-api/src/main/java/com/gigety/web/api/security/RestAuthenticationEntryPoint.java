package com.gigety.web.api.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.gigety.web.api.exception.InvalidLoginResponse;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		log.error("Authentication exception :: {}", authException.getMessage());
		InvalidLoginResponse invalidLoginResponse = new InvalidLoginResponse();
		String jsonReponse = new Gson().toJson(invalidLoginResponse);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(401);
		response.getWriter().print(jsonReponse);
	}

}
