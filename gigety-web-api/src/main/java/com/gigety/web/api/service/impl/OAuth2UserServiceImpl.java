package com.gigety.web.api.service.impl;

import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.gigety.web.api.conf.db.model.User;
import com.gigety.web.api.conf.db.repo.UserRepository;
import com.gigety.web.api.exception.OAuth2AuthenticationProcessException;
import com.gigety.web.api.security.UserPrincipal;
import com.gigety.web.api.security.oauth2.user.OAuth2UserInfo;
import com.gigety.web.api.security.oauth2.user.OAuth2UserInfoFactory;
import com.gigety.web.api.util.AuthProvider;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {

	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oauth2User = super.loadUser(userRequest);
		log.debug("Loading OAuth2 User :: {}",oauth2User);
		return processOAuth2User(userRequest, oauth2User);
	}

	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oauth2User) {
		String regId = userRequest.getClientRegistration().getRegistrationId();
		log.debug("Processing OAuth2 User Registration for {}", regId);
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(regId, oauth2User.getAttributes());
		if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
			throw new OAuth2AuthenticationProcessException("Email Not found from OAuth 2 provider");
		}

		User user;
		Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
		if (userOptional.isPresent()) {
			user = userOptional.get();
			log.debug("User found :: {}", user);
			if (!user.getProvider().equals(AuthProvider.valueOf(regId))) {
				throw new OAuth2AuthenticationProcessException(
						String.format("Account is already signup up via %s. Please login via %s", user.getProvider()));
			}
			user = updateExistingUser(user, oAuth2UserInfo);
		}else {
			// Register new USER 
			user = registerNewUser(regId, oAuth2UserInfo);
			log.debug("Registering new user :: {}", user);
		}
		return UserPrincipal.create(user);

	}

	private User registerNewUser(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
		//TODO: Revisit registration process. look at gigety-ur
		User user = new User();
		user.setProvider(AuthProvider.valueOf(registrationId));
		user.setProviderId(oAuth2UserInfo.getId());
		user.setName(oAuth2UserInfo.getName());
		user.setEmail(oAuth2UserInfo.getEmail());
		user.setImageUrl(oAuth2UserInfo.getImageUrl());
		return userRepository.save(user);
	}

	private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
		existingUser.setName(oAuth2UserInfo.getName());
		existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
		return userRepository.save(existingUser);
	}

}
