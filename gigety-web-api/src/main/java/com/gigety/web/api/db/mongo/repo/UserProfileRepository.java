package com.gigety.web.api.db.mongo.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gigety.web.api.db.mongo.entity.UserProfile;

/**
 * UserProfileRepoitory - Repository for user profiles.  Mysql users accounts can create many mongo profiles.
 * Indexes are stored create at {@link com.gigety.api.conf.MongoIndexConfiguration}
 * Note: Same Collation must be set on the query as well to take advantage of indexes
 */
public interface UserProfileRepository extends MongoRepository<UserProfile, String> {
	
	Page<UserProfile> findByEmail(@Param("email") String email, Pageable pageable);
	
	
	/**
	 * regexTitleDescriptionEmailIgnoreCase - Searches title, description, email
	 * case insensitive, Index and Collation defined {@link com.gigety.api.conf.MongoIndexConfiguration}
	 * @param searchTerm
	 * @param pageable
	 * @return Page<UserProfile> TODO: handle paging.
	 */
	@Query(value="{$or: [{title: {$regex: ?0, $options: 'i' }}, "
						+ "{description: {$regex: ?0, $options: 'i' }}, "
						+ "{email: {$regex: ?0, $options: 'i' }}]}", 
			collation = "{ locale : 'en', strength: 2}")
	Page<UserProfile> regexTitleDescriptionEmailIgnoreCase(String searchTerm, Pageable pageable);
}

