package com.gigety.web.api.transformer.impl;

import java.util.function.Function;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.gigety.web.api.conf.db.model.User;
import com.gigety.web.api.payload.SignupRequest;
import com.gigety.web.api.transformer.UserConverter;
import com.gigety.web.api.util.AuthProvider;

@Component
public class UserConverterImpl implements UserConverter {

	private final PasswordEncoder passwordEncoder;
	
	public UserConverterImpl(PasswordEncoder passwordEncoder) {
		super();
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User transformSignupRequestToUser(SignupRequest signupRequest) {
		return signupRequestToUser.apply(signupRequest);
	}
	
	private Function<SignupRequest, User> signupRequestToUser = new Function<SignupRequest, User>(){

		@Override
		public User apply(SignupRequest t) {
			User user = new User();
			user.setName(t.getName());
			user.setEmail(t.getEmail());
			user.setPassword(passwordEncoder.encode(t.getPassword()));
			user.setProvider(AuthProvider.local);
			return user;
		}
		
	};

}
