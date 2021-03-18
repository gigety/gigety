package com.gigety.web.api.service.impl;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.gigety.web.api.db.mongo.entity.Contact;
import com.gigety.web.api.db.mongo.entity.UserAccount;
import com.gigety.web.api.db.mongo.repo.UserAccountRepository;
import com.gigety.web.api.exception.ResourceNotFoundException;
import com.gigety.web.api.service.UserAccountService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAccountServiceImpl implements UserAccountService {

	private final UserAccountRepository uaRepo;
	private final MongoTemplate mongoTemplate;

	/**
	 * Get User Account - Note that resulting user account will include all user
	 * profiles from the userProfile mongo collection. THis is done using
	 * MatchOperation and LookupOperation, subclasses of AggregationOperation
	 * https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongo.aggregation
	 */
	@Override
	public UserAccount getUserAccount(String email) throws ResourceNotFoundException {

		MatchOperation match = Aggregation.match(Criteria.where("email").is(email));
		LookupOperation query = Aggregation.lookup("userProfile", "email", "email", "allProfiles");
		Aggregation agr = Aggregation.newAggregation(query, match);
		AggregationResults<UserAccount> result = mongoTemplate.aggregate(agr, "userAccount", UserAccount.class);
		if (result.getMappedResults().isEmpty()) {
			throw new ResourceNotFoundException("userAccount", "email", email);
		} else {
			UserAccount ua = result.getMappedResults().get(0);
			log.debug("FOUND User Account: {}", ua);
			return ua;
		}

	}

	@Override
	public UserAccount createUserAccount(UserAccount userAccount) {
		return uaRepo.save(userAccount);
	}

	@Override
	public UserAccount updateUserAccount(UserAccount userAccount) {
		return uaRepo.save(userAccount);
	}

	@Override
	public UserAccount setActiveContact(String mysqlUserId, Contact contact) {
		Query uaQuery = new Query();
		uaQuery.addCriteria(Criteria.where("mysqlUserId").is(mysqlUserId));
		Update update = new Update();
		update.set("activeContact", contact);
		UserAccount ua = mongoTemplate.findAndModify(
				uaQuery, 
				update, 
				new FindAndModifyOptions().returnNew(true),
				UserAccount.class);

		return ua;
	}

}
