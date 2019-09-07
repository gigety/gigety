package com.gigety.ur.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.db.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
	VerificationToken findByToken(String token);
	void deleteByGigUser(GigUser gigUser);
	void deleteByGigUserId(Long userId);
}
