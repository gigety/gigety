package com.gigety.web.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gigety.web.api.db.mongo.entity.ProfileImage;
import com.gigety.web.api.db.mongo.entity.UserProfile;
import com.gigety.web.api.exception.GigetyException;

public interface UserProfileService {
	UserProfile createUserProfile(UserProfile userProfile) throws GigetyException;
	UserProfile setProfileImage(UserProfile userProfile, ProfileImage profileImage);
	UserProfile findById(String id);
	Page<UserProfile> findAll(Pageable pageable);
	Page<UserProfile> searchProfiles(String searchTerm, Pageable pageable);
}
