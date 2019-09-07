package com.gigety.ur;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.db.model.SecurityQuestion;
import com.gigety.ur.db.model.UserSecurityQuestion;
import com.gigety.ur.db.repo.GigUserRepository;
import com.gigety.ur.service.SecurityQuestionService;

import lombok.extern.slf4j.Slf4j;

@Profile({"dev", "inmemory"})
@Configuration
@Slf4j
public class AppInitializer{

	private final GigUserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final SecurityQuestionService securityQuetionService; 



	public AppInitializer(GigUserRepository userRepo, PasswordEncoder passwordEncoder,
			SecurityQuestionService securityQuetionService) {
		super();
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
		this.securityQuetionService = securityQuetionService;
	}
	
	@Profile("inmemory")
	@EventListener(ContextRefreshedEvent.class)
	public void loadSecurityQuestions(ContextRefreshedEvent event) {
		
		//Initialize security test questions
		final SecurityQuestion q1 = new SecurityQuestion("Name your first pet?");
		final SecurityQuestion q2 = new SecurityQuestion("Name your favorite comedian?");
		List<SecurityQuestion> qList = Arrays.asList(q1,q2);
		qList.forEach(question->{
			log.debug("Adding SecurityQuestion {}", question);
		});		
		securityQuetionService.saveSecurityQuestions(qList);
		

		//Initialize dev user
//		GigUser user = new GigUser();
//		user.setEmail("samuelmosessegal@gmail.com");
//		user.setEnabled(true);
//		String pw = passwordEncoder.encode("password");
//		user.setPassword(pw);
//		user.setPasswordConfirmation(pw);
//		user.setUserSecurityQuestion(new UserSecurityQuestion(q1,user,"first Pet" ));
//		
//		log.debug("Adding dev user : {}", user);
//		userRepo.save(user);
		
		//securityQuetionService.saveUserSecurityQuestion(user, q1, "first pet");
		
	}

}
