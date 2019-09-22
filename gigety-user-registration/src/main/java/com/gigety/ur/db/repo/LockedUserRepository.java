package com.gigety.ur.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gigety.ur.db.model.LockedUser;

public interface LockedUserRepository extends JpaRepository<LockedUser, Long> {
	LockedUser findByLockedUserName(String userName);
}
