package com.gigety.web.api.service.impl;

import org.springframework.stereotype.Service;

import com.gigety.web.api.db.sql.model.User;
import com.gigety.web.api.db.sql.repo.UserRepository;
import com.gigety.web.api.exception.ResourceNotFoundException;
import com.gigety.web.api.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(()->
			new ResourceNotFoundException("user", "email", email)
		);
	}

	@Override
	public String findUserImagerUrlById(Long id) {
		
		String imageUrl = userRepository.findImageUrlById(id);
		log.debug("Image URL Found {}",imageUrl);
		return imageUrl;
	}

}
