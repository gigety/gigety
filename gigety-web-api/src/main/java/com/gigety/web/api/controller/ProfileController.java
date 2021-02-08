package com.gigety.web.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigety.web.api.db.mongo.entity.ProfileImage;
import com.gigety.web.api.db.mongo.entity.UserProfile;
import com.gigety.web.api.exception.GigetyException;
import com.gigety.web.api.security.CurrentUser;
import com.gigety.web.api.security.UserPrincipal;
import com.gigety.web.api.service.UserProfileService;
import com.gigety.web.api.util.ImageUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {

	private final UserProfileService userProfileService;

	@PostMapping(value = "/createProfile", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public UserProfile createProfile(
			@CurrentUser UserPrincipal userPrincipal, 
			@RequestPart String userProfile,
			@RequestPart("file") List<MultipartFile> file) {
		try {
			log.debug("User Principal :: {}", userPrincipal);
			log.debug("User Profile to create :: {}", userProfile);
			UserProfile up = mapUserProfile(userProfile);
			up.setEmail(userPrincipal.getEmail());
			up.setUserId(String.valueOf(userPrincipal.getId()));
			if (file != null && !file.isEmpty()) {
				InputStream is = file.get(0).getInputStream();
				byte[] buffer = new byte[is.available()];
				is.read(buffer);
				Binary binary = new Binary(BsonBinarySubType.BINARY, buffer);
				ProfileImage profileImage = new ProfileImage(userPrincipal.getEmail(), binary);
				up.setProfileImage(profileImage);
			}
			return userProfileService.createUserProfile(up);
		} catch (Exception e) {
			throw new GigetyException(e.getMessage());
		}

	}

	@PostMapping(value = "/createProfileNoImage")
	public UserProfile createProfileBroke(
			@CurrentUser UserPrincipal userPrincipal, 
			@RequestPart String userProfile
			) {
		try {
			log.debug("User Principal :: {}", userPrincipal);
			log.debug("User Profile to create :: {}", userProfile);
			UserProfile up = mapUserProfile(userProfile);
			up.setEmail(userPrincipal.getEmail());
			up.setUserId(String.valueOf(userPrincipal.getId()));

			Binary binary = new Binary(BsonBinarySubType.BINARY, ImageUtils.copyURLToByteArray(up.getUserImageUrl()));
			ProfileImage profileImage = new ProfileImage(userPrincipal.getEmail(), binary);
			up.setProfileImage(profileImage);

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

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> removeProfileById(@CurrentUser UserPrincipal principal, @PathVariable String id){
		
		try {			
			userProfileService.removeUserProfile(id);
			log.debug("Profile with id {} has been removed with success", id);
		}catch(Exception e) {
			throw new GigetyException("An error occurred removing profile with id " + id);
		}
		return ResponseEntity.ok().body("Gig Removed");
	}
	
	private UserProfile mapUserProfile(String user) {

		UserProfile userProfile = UserProfile.builder().build();

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
