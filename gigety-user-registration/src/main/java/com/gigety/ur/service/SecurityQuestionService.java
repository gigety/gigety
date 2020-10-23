package com.gigety.ur.service;

import java.util.List;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.db.model.SecurityQuestion;
import com.gigety.ur.db.model.UserSecurityQuestion;

public interface SecurityQuestionService {
	List<SecurityQuestion> findAll();
	UserSecurityQuestion findByUser(GigUser gigUser);
	void saveUserSecurityQuestion(GigUser gigUser, SecurityQuestion securityQuestion, String answeroio);
	SecurityQuestion findQuestionById(Long id);
	void saveSecurityQuestions(List<SecurityQuestion> questions);
}
