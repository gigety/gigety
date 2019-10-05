package com.gigety.web.api.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.gigety.web.api.db.model.OauthProvider;
import com.gigety.web.api.db.repo.OauthProviderRepository;
import com.gigety.web.api.service.OauthProviderService;
import com.gigety.web.api.util.AuthProvider;

@Service
public class OauthProviderServiceImpl implements OauthProviderService{

	private OauthProviderRepository oauthProviderRepo;
	@Override
	public OauthProvider findByName(String name) {
		return oauthProviderRepo.findByName(AuthProvider.valueOf(name));
	}

	@Override
	public Set<OauthProvider> getAll() {
		return new HashSet<>(oauthProviderRepo.findAll());
	}

}
