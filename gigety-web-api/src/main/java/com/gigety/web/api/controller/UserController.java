package com.gigety.web.api.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gigety.web.api.conf.db.model.User;
import com.gigety.web.api.conf.db.repo.UserRepository;
import com.gigety.web.api.exception.ResourceNotFoundException;
import com.gigety.web.api.security.CurrentUser;
import com.gigety.web.api.security.UserPrincipal;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserController {

	private final UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@GetMapping("/user/me")
	@PreAuthorize("hasRole('USER')")
	public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
		log.debug("Getting Current User from principal :: {}", userPrincipal);
		return userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
	}

}
