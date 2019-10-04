package com.gigety.web.api.conf.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ConfigurationProperties(prefix = "gig.endpoints.web.cors")
@Getter
@AllArgsConstructor
@ToString
public class GigCorsProperties {

	private final List<String> allowedOrigins;
	private final List<String> allowedMethods;
	
}
