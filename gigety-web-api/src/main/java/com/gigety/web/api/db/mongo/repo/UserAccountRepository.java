package com.gigety.web.api.db.mongo.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.gigety.web.api.db.mongo.entity.UserAccount;

public interface UserAccountRepository extends MongoRepository<UserAccount, String> {
	
	UserAccount findByEmail(@Param("email") String email);
	Optional<UserAccount> findByMysqlUserId(@Param("mysqlUserId") String mysqlUserId);
	
}
