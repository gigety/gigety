package com.gigety.web.api.service.impl;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.gigety.web.api.db.sql.model.UserOauthProvider;
import com.gigety.web.api.db.sql.model.UserOauthProviderIdentity;
import com.gigety.web.api.db.sql.repo.UserOauthProviderRepository;
import com.gigety.web.api.service.UserOauthProviderService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserOauthProviderServiceImpl implements UserOauthProviderService{

	private final UserOauthProviderRepository userOauthProviderRepository;
	@Override
	public UserOauthProvider save(UserOauthProvider userOauthProvider) {
		return userOauthProviderRepository.save(userOauthProvider);
	}
	@Override
	public Set<UserOauthProvider> findByUserId(Long id) {
		return userOauthProviderRepository.findByUserOauthProviderIdentityUserId(id);
	}
	@Override
	public Optional<UserOauthProvider> findByUserIdAndProviderId(Long userId, Long oauthProviderId) {
		return userOauthProviderRepository.findByUserOauthProviderIdentity(new UserOauthProviderIdentity(userId, oauthProviderId));
	}

}
