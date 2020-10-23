package com.gigety.web.api.service.impl;

import org.springframework.stereotype.Service;

import com.gigety.web.api.db.model.User;
import com.gigety.web.api.db.repo.UserRepository;
import com.gigety.web.api.exception.ResourceNotFoundException;
import com.gigety.web.api.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(()->
			new ResourceNotFoundException("user", "email", email)
		);
	}

}
