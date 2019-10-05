package com.gigety.web.api.service;

import java.util.Set;

import com.gigety.web.api.db.model.OauthProvider;

public interface OauthProviderService {
	OauthProvider findByName(String name);
	Set<OauthProvider> getAll();
}
