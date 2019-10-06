package com.gigety.web.api.service.cached.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gigety.web.api.db.model.OauthProvider;
import com.gigety.web.api.db.repo.OauthProviderRepository;
import com.gigety.web.api.service.cached.OauthProviderService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class OauthProviderServiceImpl implements OauthProviderService{

	private final OauthProviderRepository oauthProviderRepo;
	
	@Override
	@Cacheable(value = "oauthProviderCache", key = "#name", unless="#result == null")
	public Optional<OauthProvider> findByName(String name) {
		log.debug("Findindg oauthProvider {} ...", name);
		return oauthProviderRepo.findByName(name);
	}

	@Override
	@Cacheable(value = "oauthProviderCache")
	public Set<OauthProvider> getAll() {
		return new HashSet<>(oauthProviderRepo.findAll());
	}

}
