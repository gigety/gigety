package com.gigety.ur.db.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gigety.ur.db.model.GigUser;

public interface GigUserRepository extends JpaRepository<GigUser, Long> {
	
	GigUser findByEmail(String email);
	List<GigUser> findByEmailIn(List<String> emails);
	void deleteById(String id);
}
