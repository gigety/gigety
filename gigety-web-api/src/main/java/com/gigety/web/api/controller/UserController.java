package com.gigety.web.api.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gigety.web.api.db.model.User;
import com.gigety.web.api.db.repo.UserRepository;
import com.gigety.web.api.exception.ResourceNotFoundException;
import com.gigety.web.api.security.CurrentUser;
import com.gigety.web.api.security.UserPrincipal;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
public class UserController {

	private final UserRepository userRepository;
	

	@GetMapping("/user/me")
	@PreAuthorize("hasRole('USER')")
	public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
		System.out.println("Principal: " + userPrincipal.getEmail());
		
		return userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
	}
	
	@GetMapping("/user/edit")
	@PreAuthorize("hasRole('USER')")
	public User editUser(@CurrentUser UserPrincipal userPrincipal, User user) {
		log.debug("Updating User: {}", user);
		return userRepository.save(user);
	}
	

}
