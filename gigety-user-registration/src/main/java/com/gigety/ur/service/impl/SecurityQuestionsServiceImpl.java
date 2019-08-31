package com.gigety.ur.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.db.model.SecurityQuestion;
import com.gigety.ur.db.model.UserSecurityQuestion;
import com.gigety.ur.db.repo.SecurityQuestionRepository;
import com.gigety.ur.db.repo.UserSecurityQuestionRepository;
import com.gigety.ur.service.SecurityQuestionService;

/**
 * Security Question Service
 */
@Service
public class SecurityQuestionsServiceImpl implements SecurityQuestionService {

	final SecurityQuestionRepository questionRepo;
	final UserSecurityQuestionRepository userSecurityRepo;

	public SecurityQuestionsServiceImpl(SecurityQuestionRepository questionRepo,
			UserSecurityQuestionRepository userSecurityRepo) {
		super();
		this.questionRepo = questionRepo;
		this.userSecurityRepo = userSecurityRepo;
	}

	@Override
	public List<SecurityQuestion> findAll() {
		return questionRepo.findAll();
	}

	@Override
	public UserSecurityQuestion findByUser(
			GigUser gigUser) {
		return userSecurityRepo.findByGigUserId(gigUser.getId());
	}

	@Override
	public void saveUserSecurityQuestion(
			GigUser gigUser,
			SecurityQuestion securityQuestion,
			String answer) {
		userSecurityRepo.save(new UserSecurityQuestion(securityQuestion, gigUser, answer));
	}

	@Override
	public SecurityQuestion findQuestionById(
			Long id) {
		return questionRepo.getOne(id);
	}

	@Override
	public void saveSecurityQuestions(List<SecurityQuestion> questions) {
		questionRepo.saveAll(questions);
	}

}
