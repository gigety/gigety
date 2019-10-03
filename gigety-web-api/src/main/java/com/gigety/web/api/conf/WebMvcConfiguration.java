package com.gigety.web.api.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.gigety.web.api.conf.properties.GigCorsProperties;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@AllArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {

	private final long MAX_AGE_SECS = 3600;
	
	private final GigCorsProperties gigCorsProperties;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		log.debug("Setting CORS Properties: {}", gigCorsProperties);
		String [] props = gigCorsProperties.getAllowedOrigins().stream().toArray(String[]::new);
		registry
			.addMapping("/**")
			.allowedOrigins(props)
			.allowedMethods("*")	
			.allowCredentials(true)
			.maxAge(MAX_AGE_SECS);
	}
	
}
