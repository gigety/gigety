package com.gigety.web.api.service.impl;

import java.util.Optional;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.gigety.web.api.db.model.OauthProvider;
import com.gigety.web.api.db.model.User;
import com.gigety.web.api.db.model.UserOauthProvider;
import com.gigety.web.api.db.model.UserOauthProviderIdentity;
import com.gigety.web.api.db.repo.OauthProviderRepository;
import com.gigety.web.api.db.repo.UserOauthProviderRepository;
import com.gigety.web.api.db.repo.UserRepository;
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
	private final OauthProviderRepository oauthProviderRepository;
	private final UserOauthProviderRepository userOauthProviderRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oauth2User = super.loadUser(userRequest);
		log.debug("Loading OAuth2 User :: {}",oauth2User);
		try {
			return processOAuth2User(userRequest, oauth2User);
		} catch (Exception e) {
			log.error("Error processsing OAUTH2 USER :: {}",e.getMessage(),e);
			throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
		}
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
//			if (!user.getProvider().equals(AuthProvider.valueOf(regId))) {
//				throw new OAuth2AuthenticationProcessException(
//						String.format("Account is already signup up via %s.", user.getProvider().name()));
//			}
			user = updateExistingUser(user, oAuth2UserInfo);
		}else {
			// Register new USER 
			user = registerNewUser(regId, oAuth2UserInfo);
			log.debug("Registering new user :: {}", user);
		}
		return UserPrincipal.create(user, oauth2User.getAttributes());

	}

	private User registerNewUser(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
		//TODO: Revisit registration process. look at gigety-ur

		User user = new User();

		user.setName(oAuth2UserInfo.getName());
		user.setEmail(oAuth2UserInfo.getEmail());
		user.setImageUrl(oAuth2UserInfo.getImageUrl());
		user.setProvider(AuthProvider.valueOf(registrationId));
		user = userRepository.save(user);
//		//TODO: replace this query with a cached service for providers. Go Redis!!
		OauthProvider provider = oauthProviderRepository.findByName(AuthProvider.valueOf(registrationId));
		UserOauthProvider userProvider = new UserOauthProvider(new UserOauthProviderIdentity(user.getId(),provider.getId()), oAuth2UserInfo.getId());
		userOauthProviderRepository.save(userProvider);
		return user;
	}

	private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
		existingUser.setName(oAuth2UserInfo.getName());
		existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
		return userRepository.save(existingUser);
	}

}
