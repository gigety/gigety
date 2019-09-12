package com.gigety.ur.service.impl;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gigety.ur.service.RunAsService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RunAsServiceImpl implements RunAsService {

	@Override
	@Secured({"ROLE_RUN_AS_REPORTER"})
	public Authentication getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		log.debug("Get Current User auth: {}", auth);
		return auth;
	}
}
