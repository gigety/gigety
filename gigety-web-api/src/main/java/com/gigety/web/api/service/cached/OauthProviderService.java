package com.gigety.web.api.service.cached;

import java.util.Optional;
import java.util.Set;

import com.gigety.web.api.db.model.OauthProvider;

public interface OauthProviderService {
	Optional<OauthProvider> findByName(String name);
	Set<OauthProvider> getAll();
}
