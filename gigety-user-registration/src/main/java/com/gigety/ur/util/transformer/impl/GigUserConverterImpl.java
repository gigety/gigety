package com.gigety.ur.util.transformer.impl;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.util.transformer.GigUserConverter;

//@Component
public class GigUserConverterImpl implements GigUserConverter{

	
	/**
	 * DEMOSTRATION PURPOSE ONLY - UNLESS YOUR NOT WORRIED ABOUT ID's
	 * transformEmailsToGigUsers - transforms a list of email to gigUsers to a 
	 * list of gigUserObjects, this is pretty pointless unless you want a list
	 * gigUserObject with only email populated. Am leaving this for 
	 * demonstration of object transformation using Functional style solution.
	 */
	public List<GigUser> transformEmailsToGigUsers(List<String> emails){
		return emails.stream().map(emailToGigUser).collect(Collectors.<GigUser> toList());
	}
	
	
	private Function<String, GigUser> emailToGigUser = new Function<String, GigUser>(){

		@Override
		public GigUser apply(String email) {
			GigUser u = new GigUser();
			u.setEmail(email);
			return u;
		}
		
	};
}
