package com.gigety.ur.security;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

/**
 * Overrides DefaultMethodSecurityExpressionHandler createSecurityExpressionRoot
 * Here we can plug in our custom Security ExpressionRoot which can create 
 * custom method level programttic expressions to be used in @Preauthorize
 * annotations.
 * 
 * @see GigSecurityExpressionRoot
 */
public class GigMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

	@Override
	protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication,
			MethodInvocation invocation) {
		
		GigSecurityExpressionRoot root = new GigSecurityExpressionRoot(authentication);
		root.setThis(invocation.getThis());
		root.setPermissionEvaluator(getPermissionEvaluator());
		root.setTrustResolver(getTrustResolver());
		root.setRoleHierarchy(getRoleHierarchy());
		root.setDefaultRolePrefix(getDefaultRolePrefix());

		return root;
	}

}
