package com.gigety.web.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigety.web.api.db.model.User;
import com.gigety.web.api.db.mongo.entity.ProfileImage;
import com.gigety.web.api.db.mongo.entity.UserProfile;
import com.gigety.web.api.exception.GigetyException;
import com.gigety.web.api.security.CurrentUser;
import com.gigety.web.api.security.UserPrincipal;
import com.gigety.web.api.service.UserProfileService;
import com.gigety.web.api.service.UserService;
import com.gigety.web.api.util.ImageUtils;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {

	private final UserProfileService userProfileService;

	@PostMapping(value = "/createProfile", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public UserProfile createProfile(@CurrentUser UserPrincipal userPrincipal, @RequestPart String userProfile,
			@RequestPart("file") List<MultipartFile> file, BindingResult result) {
		try {
			log.debug("User Principal :: {}", userPrincipal);
			log.debug("User Profile to create :: {}", userProfile);
			UserProfile up = mapUserProfile(userProfile, file);
			up.setEmail(userPrincipal.getEmail());
			if (file != null && !file.isEmpty()) {
				InputStream is = file.get(0).getInputStream();
				byte[] buffer = new byte[is.available()];
				is.read(buffer);
				Binary binary = new Binary(BsonBinarySubType.BINARY, buffer);
				ProfileImage profileImage = new ProfileImage(userPrincipal.getEmail(), binary);
				up.setProfileImage(profileImage);
			} else {

				Binary binary = new Binary(BsonBinarySubType.BINARY, ImageUtils.copyURLToByteArray(up.getUserImageUrl()));
				ProfileImage profileImage = new ProfileImage(userPrincipal.getEmail(), binary);
				up.setProfileImage(profileImage);
			}
			return userProfileService.createUserProfile(up);
		} catch (Exception e) {
			throw new GigetyException(e.getMessage());
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<UserProfile> getProfileById(@PathVariable(value = "id") String id) {
		UserProfile userProfile = userProfileService.findById(id);
		return ResponseEntity.ok().body(userProfile);
	}

	private UserProfile mapUserProfile(String user, List<MultipartFile> file) {

		UserProfile userProfile = new UserProfile();

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			userProfile = objectMapper.readValue(user, UserProfile.class);
		} catch (IOException err) {
			log.error("Error: {}", err.getMessage());
			throw new GigetyException(err.getMessage());
		}

		return userProfile;

	}

}
