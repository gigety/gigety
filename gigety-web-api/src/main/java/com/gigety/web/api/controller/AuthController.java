package com.gigety.web.api.controller;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gigety.web.api.db.sql.model.User;
import com.gigety.web.api.db.sql.repo.UserRepository;
import com.gigety.web.api.exception.BadRequestException;
import com.gigety.web.api.payload.ApiResponse;
import com.gigety.web.api.payload.AuthResponse;
import com.gigety.web.api.payload.LoginRequest;
import com.gigety.web.api.payload.SignupRequest;
import com.gigety.web.api.security.JwtTokenProvider;
import com.gigety.web.api.transformer.UserConverter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserConverter userConverter;


	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		log.debug("authenticating request :: {}", loginRequest);
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		log.debug("got authentication :: {}", authentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.createToken(authentication);
		log.debug("Returning token :: {}", token);
		return ResponseEntity.ok(new AuthResponse(token));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
		log.debug("Attempting to register {}", signupRequest.getEmail());
		if (userRepository.existsByEmail(signupRequest.getEmail())) {
			throw new BadRequestException(String.format("Email %s is already registered", signupRequest.getEmail()));
		}

		// create new user account
		User user = userConverter.transformSignupRequestToUser(signupRequest);
		user = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/me")
				.buildAndExpand(user.getId()).toUri();
		log.debug("Setting location header to {}", location);
		return ResponseEntity.created(location).body(new ApiResponse(true, "User Registered Successfully@"));
	}


}
