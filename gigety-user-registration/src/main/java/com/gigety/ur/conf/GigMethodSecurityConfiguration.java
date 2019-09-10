package com.gigety.ur.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import com.gigety.ur.security.GigMethodSecurityExpressionHandler;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GigMethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

//	@Override
//	protected RunAsManager runAsManager() {
//		// TODO Auto-generated method stub
//		return super.runAsManager();
//	}

	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		GigMethodSecurityExpressionHandler handler = new GigMethodSecurityExpressionHandler();
		return handler;
	}
	

}


