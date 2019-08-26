package com.gigety.ur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.gigety.ur.conf.GigSecurityConf;
import com.gigety.ur.conf.GigWebMVCConf;

@SpringBootApplication
@ComponentScan("com.gigety.ur")
@EnableJpaRepositories("com.gigety.ur")
@EntityScan("com.gigety.ur.db.model")
public class GigetyUserRegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(
				new Class[] { GigetyUserRegistrationApplication.class, GigSecurityConf.class, GigWebMVCConf.class },
				args);
	}

}
