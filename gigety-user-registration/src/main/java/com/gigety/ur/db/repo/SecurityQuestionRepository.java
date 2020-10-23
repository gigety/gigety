package com.gigety.ur.db.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gigety.ur.db.model.SecurityQuestion;

public interface SecurityQuestionRepository extends JpaRepository<SecurityQuestion, Long> {
	Optional<SecurityQuestion> findById(Long id);
}
