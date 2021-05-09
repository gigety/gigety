package com.gigety.web.api.db.sql.repo;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gigety.web.api.db.sql.model.UserOauthProvider;
import com.gigety.web.api.db.sql.model.UserOauthProviderIdentity;

@Repository
public interface UserOauthProviderRepository extends JpaRepository<UserOauthProvider, UserOauthProviderIdentity> {
	Set<UserOauthProvider> findByUserOauthProviderIdentityUserId(Long userId);
	Optional<UserOauthProvider> findByUserOauthProviderIdentity(UserOauthProviderIdentity identity);
}
