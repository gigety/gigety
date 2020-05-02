package com.gigety.web.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.gigety.web.api.conf.properties.GigAuthProperties;
import com.gigety.web.api.conf.properties.GigCorsProperties;

@SpringBootApplication
@EnableConfigurationProperties({GigAuthProperties.class, GigCorsProperties.class})
public class GigetyWebApiApplication {

	public static void main(String[] args) {
		System.out.println("########## LOADING GIGETY WEB API ##############");
		System.out.println("########## LOADING GIGETY WEB API ##############");
		System.out.println("########## LOADING GIGETY WEB API ##############");
		System.out.println("########## LOADING GIGETY WEB API ##############");
		System.out.println("########## LOADING GIGETY WEB API ##############");
		System.out.println("########## LOADING GIGETY WEB API ##############");
		SpringApplication.run(GigetyWebApiApplication.class, args);
	}

}
