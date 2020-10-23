package com.gigety.web.api.db.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gigety.web.api.db.model.OauthProvider;

@Repository
public interface OauthProviderRepository extends JpaRepository<OauthProvider, Long> {
	Optional<OauthProvider> findByName(String name);
}
