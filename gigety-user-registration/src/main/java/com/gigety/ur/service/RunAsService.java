package com.gigety.ur.service;

import org.springframework.security.core.Authentication;

public interface RunAsService {
	public Authentication getCurrentUser();
}
