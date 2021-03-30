package com.gigety.web.api.service;

import java.util.Optional;
import java.util.Set;

import com.gigety.web.api.db.sql.model.UserOauthProvider;

public interface UserOauthProviderService {
	UserOauthProvider save(UserOauthProvider userOauthProvider);
	Set<UserOauthProvider> findByUserId(Long id);
	Optional<UserOauthProvider> findByUserIdAndProviderId(Long userId, Long oauthProviderId);
}
