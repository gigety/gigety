package com.gigety.ur.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.db.model.UserSecurityQuestion;

public interface UserSecurityQuestionRepository extends JpaRepository<UserSecurityQuestion, Long> {
	UserSecurityQuestion findByGigUser(GigUser user);
	UserSecurityQuestion findByGigUserId(Long id);
	void deleteByGigUserId(Long userId);
}
