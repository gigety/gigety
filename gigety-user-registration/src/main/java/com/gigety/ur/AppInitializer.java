package com.gigety.ur;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.db.model.Role;
import com.gigety.ur.db.model.SecurityQuestion;
import com.gigety.ur.db.model.UserSecurityQuestion;
import com.gigety.ur.db.repo.RoleRepository;
import com.gigety.ur.db.repo.SecurityQuestionRepository;
import com.gigety.ur.service.GigUserService;
import com.gigety.ur.service.SecurityQuestionService;
import com.gigety.ur.util.validation.EmailExistsException;

import lombok.extern.slf4j.Slf4j;

@Profile({"dev", "inmemory"})
@Configuration
@Slf4j
public class AppInitializer{

	private final SecurityQuestionService securityQuetionService; 
	private final GigUserService userService;
	private final SecurityQuestionRepository securityQuestionRepo;
	private final RoleRepository roleRepo;
	private final PasswordEncoder passwordEncoder;



	public AppInitializer(SecurityQuestionService securityQuetionService, GigUserService userService,
			SecurityQuestionRepository securityQuestionRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
		super();
		this.securityQuetionService = securityQuetionService;
		this.userService = userService;
		this.securityQuestionRepo = securityQuestionRepo;
		this.roleRepo = roleRepo;
		this.passwordEncoder = passwordEncoder;
	}

	@EventListener(ContextRefreshedEvent.class)
	public void loadData(ContextRefreshedEvent event) {
		
		List<SecurityQuestion> qList = loadSecurityQuestions();
		loadRoles();
		createUserAllRoles("samuelmosessegal@gmail.com", "password", qList.get(0));
		createUser("q@q.com", "password", qList.get(1));
		
	}

	@EventListener(ContextClosedEvent.class)
	public void removeData(ContextClosedEvent event) {
		GigUser sam = userService.findByEmail("samuelmosessegal@gmail.com");
		GigUser q = userService.findByEmail("q@q.com");
		
		log.debug("REMOVING USER {}", sam);
		userService.removeUser(sam.getId());
		
		log.debug("REMOVING USER {}", q);
		userService.removeUser(q.getId());
		
		securityQuestionRepo.deleteAll();
		
	}
	
	private void createUser(String email,
			String password,
			SecurityQuestion securityQuestion) {
		//Initialize dev user
		GigUser user = new GigUser();
		user.setEmail(email);
		user.setEnabled(true);
		user.setPassword(password);
		user.setPasswordConfirmation(password);
		
		log.debug("Adding dev user : {}", user);
		try {
			user = userService.registerNewUser(user, new UserSecurityQuestion(securityQuestion,user,"dunno" ));
			user.setEnabled(true);
			userService.updateExistingUser(user);
			
		} catch (EmailExistsException | Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void createUserAllRoles(String email,
			String password,
			SecurityQuestion securityQuestion) {
		//Initialize dev user
		GigUser user = new GigUser();
		user.setEmail(email);
		user.setEnabled(true);
		user.setPassword(password);
		user.setPasswordConfirmation(password);
		user.setRoles(roleRepo.findAll());
		
		log.debug("Adding dev user : {}", user);
		try {
			user = userService.registerNewUser(user, new UserSecurityQuestion(securityQuestion,user,"dunno" ));
			user.setEnabled(true);
			userService.updateExistingUser(user);
			
		} catch (EmailExistsException | Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private void loadRoles() {
		roleRepo.save(new Role("ROLE_GIG_ADMIN"));
	}
	
	private List<SecurityQuestion> loadSecurityQuestions() {
		final SecurityQuestion q1 = new SecurityQuestion("Name your first pet?");
		final SecurityQuestion q2 = new SecurityQuestion("Name your favorite comedian?");
		List<SecurityQuestion> qList = Arrays.asList(q1,q2);
		qList.forEach(question->{
			log.debug("Adding SecurityQuestion {}", question);
		});		
		securityQuetionService.saveSecurityQuestions(qList);
		return qList;
	}

}
