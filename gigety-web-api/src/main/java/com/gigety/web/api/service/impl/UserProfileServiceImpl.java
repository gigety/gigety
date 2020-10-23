package com.gigety.web.api.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gigety.web.api.db.mongo.entity.ProfileImage;
import com.gigety.web.api.db.mongo.entity.UserProfile;
import com.gigety.web.api.db.mongo.repo.UserProfileRepository;
import com.gigety.web.api.exception.GigetyException;
import com.gigety.web.api.exception.ResourceNotFoundException;
import com.gigety.web.api.service.UserProfileService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {

	private final UserProfileRepository userProfileRepository;
	//private final MongoTemplate mongoTemplate;
	//private final MongoOperations mongoOperations;

	@Override
	public UserProfile createUserProfile(UserProfile userProfile) throws GigetyException {
		try {
			UserProfile up = userProfileRepository.insert(userProfile);
			log.debug("UserProfile created :: {}", userProfile.getTitle());
			return up;
		} catch (Exception e) {
			log.error("Error creating UserProfile :: {}", e.getMessage(), e);
			throw new GigetyException(e.getMessage());
		}
	}

	@Override
	public UserProfile setProfileImage(UserProfile userProfile, ProfileImage profileImage) throws GigetyException {
		try {
			userProfile.setProfileImage(profileImage);
			return userProfileRepository.save(userProfile);
		} catch (Exception e) {
			log.error("Errer setting User Profile Image :: {}", e.getMessage(), e);
			throw new GigetyException(e.getMessage());
		}

	}

	@Override
	public UserProfile findById(String id) throws ResourceNotFoundException {
		return userProfileRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("UserProfile", "id", id));
	}

	@Override
	public Page<UserProfile> findAll(Pageable pageable) {

		return userProfileRepository.findAll(pageable);
	}

	@Override
	public Page<UserProfile> searchProfiles(String searchTerm, Pageable pageable) {

		Page<UserProfile> results = userProfileRepository.regexTitleDescriptionEmailIgnoreCase(searchTerm, pageable);
		log.debug("Found {} profiles matching search terms {}", searchTerm);

		
//		Query query = new Query();
//		query
//			.addCriteria(new Criteria().orOperator(
//							Criteria.where("title").regex(searchTerm, "i"),
//							Criteria.where("description").regex(searchTerm, "i")))
//			.collation(
//					Collation.of(Locale.ENGLISH).strength(ComparisonLevel.secondary()));
//		List<UserProfile> profiles = mongoTemplate.find(query,UserProfile.class);
//
//		long count = mongoOperations.count(query, UserProfile.class);
//		Page<UserProfile> results = new PageImpl<UserProfile>(profiles, pageable,count);
		
		return results;
	}

}
