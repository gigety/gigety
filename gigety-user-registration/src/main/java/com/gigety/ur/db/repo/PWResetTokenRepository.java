package com.gigety.ur.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.db.model.PWResetToken;

public interface PWResetTokenRepository extends JpaRepository<PWResetToken, Long> {
	PWResetToken findByToken(String token);
	void deleteByGigUser(GigUser gigUser);
	void deleteByGigUserId(Long id);
}
