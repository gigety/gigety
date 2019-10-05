package com.gigety.web.api.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gigety.web.api.db.model.OauthProvider;
import com.gigety.web.api.util.AuthProvider;

@Repository
public interface OauthProviderRepository extends JpaRepository<OauthProvider, Long> {
	OauthProvider findByName(AuthProvider name);
}
