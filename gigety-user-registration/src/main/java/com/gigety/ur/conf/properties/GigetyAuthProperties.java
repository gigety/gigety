package com.gigety.ur.conf.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;

@ConfigurationProperties(prefix = "gigety")
@Getter
public class GigetyAuthProperties {
	
	private final List<String> redirectUris = new ArrayList<>();

}
