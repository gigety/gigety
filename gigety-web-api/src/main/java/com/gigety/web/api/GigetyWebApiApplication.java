package com.gigety.web.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.gigety.web.api.conf.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class GigetyWebApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GigetyWebApiApplication.class, args);
	}

}
