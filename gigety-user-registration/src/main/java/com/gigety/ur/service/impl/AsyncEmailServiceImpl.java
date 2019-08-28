package com.gigety.ur.service.impl;

import java.util.Locale;
import java.util.UUID;

import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.service.AsyncEmailService;
import com.gigety.ur.service.GigUserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AsyncEmailServiceImpl implements AsyncEmailService {
	
	private final JavaMailSender mailSender;
	private final GigUserService userService;
	private final MessageSource messageSource;


	public AsyncEmailServiceImpl(JavaMailSender mailSender, GigUserService userService, MessageSource messageSource) {
		super();
		this.mailSender = mailSender;
		this.userService = userService;
		this.messageSource = messageSource;
	}

	@Override
	@Async
	public void sendRegistrationConfirmationEmail(GigUser user, String appUrl, Locale locale) {

		final String token = UUID.randomUUID().toString();
		
		userService.assignVerificationToken(user, token);
        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = appUrl + "/reg/confirm-reg?token=" + token;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(confirmationUrl);
        log.debug("Sending mail : {}", email);
        mailSender.send(email);	
	}

	@Override
	@Async
	public void sendPWResetEmail(GigUser user, String appUrl, Locale locale) {
		final String token = UUID.randomUUID().toString();
		
		userService.assignResetPWToken(user, token);
        String recipientAddress = user.getEmail();
        String subject = messageSource.getMessage("email.pw.reset", null, locale);
        String confirmationUrl = appUrl + "/reg/update-pw?token=" + token;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(confirmationUrl);
        log.debug("Sending mail : {}", email);
        mailSender.send(email);			
	}

}
