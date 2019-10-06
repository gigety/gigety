package com.gigety.web.api.transformer.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.gigety.web.api.db.model.OauthProvider;
import com.gigety.web.api.db.model.User;
import com.gigety.web.api.payload.SignupRequest;
import com.gigety.web.api.service.cached.OauthProviderService;
import com.gigety.web.api.transformer.UserConverter;
import com.gigety.web.api.util.AuthProviderConstants;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserConverterImpl implements UserConverter {

	@NonNull
	private final PasswordEncoder passwordEncoder;
	@NonNull
	private final OauthProviderService oauthProviderService;

	@Override
	public User transformSignupRequestToUser(SignupRequest signupRequest) {
		return signupRequestToUser.apply(signupRequest);
	}

	private Function<SignupRequest, User> signupRequestToUser = new Function<SignupRequest, User>() {

		@Override
		public User apply(SignupRequest t) {
			User user = new User();
			user.setName(t.getName());
			user.setEmail(t.getEmail());
			user.setPassword(passwordEncoder.encode(t.getPassword()));
			// TODO: THis needs to be cached in the service
			OauthProvider localProvider = oauthProviderService.findByName(AuthProviderConstants.LOCAL).get();
			Set<OauthProvider> providers = new HashSet<>();
			providers.add(localProvider);
			user.setOauthProviders(providers);
			user.setProvider(AuthProviderConstants.LOCAL);
			return user;
		}

	};

}
