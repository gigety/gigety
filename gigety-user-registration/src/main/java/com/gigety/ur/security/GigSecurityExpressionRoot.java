package com.gigety.ur.security;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.gigety.ur.conf.GigMethodSecurityConfiguration;
import com.gigety.ur.web.controller.UserController;


/**
 * GigSecurityExpressionRoot 
 * This demonstrates custom security programmatic expression that can be used 
 * at method level for @Preauthorize annotations. Currently this is for 
 * demonstration purpose only with the isGigAdmin() method.
 * 
 * Note: these methods cannot be used in default tag libraries via thymeleaf 
 * or jsp.
 * 
 * @see GigMethodSecurityConfiguration
 * @see GigMethodSecurityExpressionHandler
 * @see UserController - notice its usage in @Preauthorize.
 */
public class GigSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations{

	
	public GigSecurityExpressionRoot(Authentication authentication) {
		super(authentication);
	}

	/**
	 * Custom security programmatic expression isGigAdmin()
	 * THis can be used in method level security annotations
	 * @Preauthorize 
	 * @return
	 */
	public boolean isGigAdmin() {
        if (!(getPrincipal() instanceof User)) {
            return false;
        }
		User user = (User)getPrincipal();
		return user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"));
	}

	/**
	 * Following properties are: MethodSecurityExpressionOperations Specific
	 * copied values from springs default MethodSecurityExpressionRoot
	 * @see org.springframework.security.access.expression.method.MethodSecurityExpressionRoot
	 */
	
	private Object filterObject;
	private Object returnObject;
	private Object target;
	
	public void setFilterObject(Object filterObject) {
		this.filterObject = filterObject;
	}

	public Object getFilterObject() {
		return filterObject;
	}

	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}

	public Object getReturnObject() {
		return returnObject;
	}

	/**
	 * Sets the "this" property for use in expressions. Typically this will be the "this"
	 * property of the {@code JoinPoint} representing the method invocation which is being
	 * protected.
	 *
	 * @param target the target object on which the method in is being invoked.
	 */
	void setThis(Object target) {
		this.target = target;
	}

	public Object getThis() {
		return target;
	}
}
