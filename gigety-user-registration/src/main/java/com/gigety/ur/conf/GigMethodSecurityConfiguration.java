package com.gigety.ur.conf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.intercept.RunAsManager;
import org.springframework.security.access.intercept.RunAsManagerImpl;
import org.springframework.security.access.method.MapBasedMethodSecurityMetadataSource;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import com.gigety.ur.security.GigMethodSecurityExpressionHandler;
import com.gigety.ur.security.GigSecurityExpressionRoot;
import com.gigety.ur.util.GigConstants;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class GigMethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

	
	@Override
	protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
		Map<String, List<ConfigAttribute>> methodMap = new HashMap<>();
		methodMap.put("com.gigety.ur.web.controller.UserController.create*", SecurityConfig.createList("ROLE_ADMIN"));
		return new MapBasedMethodSecurityMetadataSource(methodMap);
	}

	/**
	 * overriding runAsManager() to provide a RunAsManagerImpl. THis allows
	 * users to be provided permissions required access to otherwise protected
	 * resources?
	 */
	@Override
	protected RunAsManager runAsManager() {
		final RunAsManagerImpl runAsManager = new RunAsManagerImpl();
		runAsManager.setKey(GigConstants.RUN_AS_KEY);
		return runAsManager;
	}

	/**
	 * Here we plugin our GigMethodSecurityExpressionHandler in which we can
	 * use to write custom method SPEL expressions for @Preauthorize tag, 
	 * such as isGigAdmin(). 
	 * @see GigMethodSecurityExpressionHandler
	 * @see GigSecurityExpressionRoot
	 */
	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		GigMethodSecurityExpressionHandler handler = new GigMethodSecurityExpressionHandler();
		return handler;
	}
	

}


