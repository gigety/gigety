package com.gigety.web.api.db.mongo.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.gigety.web.api.db.mongo.entity.Contact;

public interface ContactsRepository extends MongoRepository<Contact, String> {
	
	List<Contact> findByUserId(@Param("userId") String userId);
	
}
