package com.gigety.web.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import com.gigety.web.api.conf.properties.GigAuthProperties;
import com.gigety.web.api.conf.properties.GigCorsProperties;

@SpringBootApplication
@EnableConfigurationProperties({ GigAuthProperties.class, GigCorsProperties.class })
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
@EnableMongoAuditing
public class GigetyWebApiApplication {

	public static void main(String[] args) {
		System.out.println("🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸");
		System.out.println("########## 🐸🐸🐸 LOADING GIGETY WEB API 🐸🐸🐸 ##############");
		System.out.println("🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸🐸");
		SpringApplication.run(GigetyWebApiApplication.class, args);
	}

}
