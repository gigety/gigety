package com.gigety.ur.security.registration;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.service.GigUserService;

import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	private final GigUserService userService;
	private final JavaMailSender mailSender;
	private final Environment env;
	
	@Autowired
	public RegistrationListener(GigUserService userService, JavaMailSender mailSender, Environment env) {
		super();
		this.userService = userService;
		this.mailSender = mailSender;
		this.env = env;
	}


	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		confirmRegistration(event);
	}
    private void confirmRegistration(OnRegistrationCompleteEvent event) {
    	
        final GigUser user = event.getUser();
		final String token = UUID.randomUUID().toString();
		
		userService.setVerificationToken(user, token);
        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = event.getAppUrl() + "/gigety/confirm-reg?token=" + token;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(confirmationUrl);
        log.debug("Sending mail : {}", email);
        mailSender.send(email);
    }
}
