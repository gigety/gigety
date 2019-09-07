package com.gigety.ur.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gigety.ur.db.model.GigUser;

public interface GigUserRepository extends JpaRepository<GigUser, Long> {
	
	GigUser findByEmail(String email);
	void deleteById(String id);
	
}
