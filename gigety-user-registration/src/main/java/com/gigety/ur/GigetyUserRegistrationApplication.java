package com.gigety.ur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.gigety.ur")
public class GigetyUserRegistrationApplication {

	/** 
	 * TODO:
	 *  -- Handle multiple login failures. Account locks maybe after determined number of failed attempts
	 *  -- Figure out why overriding DefaultMethodSecurityExpressionHandler does not work in 2.2.0
	 * 
	 */
	
	public static void main(String[] args) {
		SpringApplication.run( new Class[] {GigetyUserRegistrationApplication.class }, args);
	}

}
