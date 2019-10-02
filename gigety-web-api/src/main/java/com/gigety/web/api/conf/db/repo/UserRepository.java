package com.gigety.web.api.conf.db.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gigety.web.api.conf.db.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
	Boolean existsByEmail(String email);
	
}
