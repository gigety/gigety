package com.gigety.web.api.db.mongo.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.gigety.web.api.db.mongo.entity.Gig;

public interface GigRepository extends MongoRepository<Gig, String> {

	@Query(value = "{$or: [{title: {$regex: ?0, $options: 'i' }}, " 
						+ "{description: {$regex: ?0, $options: 'i' }}, "
						+ "{email: {$regex: ?0, $options: 'i' }}]}", 
			collation = "{ locale : 'en', strength: 2}")
	Page<Gig> regexTitleDescriptionEmailIgnoreCase(String searchTerm, Pageable pageable);
}
