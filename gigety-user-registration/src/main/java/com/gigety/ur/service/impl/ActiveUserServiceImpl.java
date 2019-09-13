package com.gigety.ur.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.gigety.ur.service.ActiveUserService;

@Service
public class ActiveUserServiceImpl implements ActiveUserService {

	private final SessionRegistry sessionRegistry;

	public ActiveUserServiceImpl(SessionRegistry sessionRegistry) {
		super();
		this.sessionRegistry = sessionRegistry;
	}

	@Override
	public List<String> getAllActiveUsers() {
		List<Object> principals = sessionRegistry.getAllPrincipals();
		User[]users = principals.toArray(new User[principals.size()]);
		return Arrays.stream(users)
			.filter(u -> 
				!(sessionRegistry.getAllSessions(u, false).isEmpty())
			)
			.map(u -> u.getUsername())
			.collect(Collectors.toList());

		
	}

}
