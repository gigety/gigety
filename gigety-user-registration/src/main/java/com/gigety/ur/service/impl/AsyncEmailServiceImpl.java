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

/**
 * Asynchronous Email Services
 * @author samuelsegal
 *
 */
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

	/**
	 * Send a confirmation email with assigned token to user. This link will enable
	 * user, literally setEnabled(true)
	 */
	@Override
	@Async
	public void sendRegistrationConfirmationEmail(GigUser user, String appUrl, Locale locale) {

		final String token = UUID.randomUUID().toString();
		userService.assignVerificationToken(user, token);
		String confirmationUrl = appUrl + "/reg/confirm-reg?token=" + token;
		String subject = messageSource.getMessage("confirmation.registration", null, locale);
		sendMail(user, confirmationUrl, subject, locale);
	}

	/**
	 * Send a password reset email with token assigned to given user
	 */
	@Override
	@Async
	public void sendPWResetEmail(GigUser user, String appUrl, Locale locale) {
		//TODO: Check if user exists, if not return message saying no user exists with that email
		final String token = UUID.randomUUID().toString();
		String confirmationUrl = String.format("%1$s%2$s%3$s%4$s%5$s", appUrl, "/reg/updatepw?id=", user.getId(),
				"&token=", token);
		userService.assignResetPWToken(user, token);
		String subject = messageSource.getMessage("email.pw.reset", null, locale);
		sendMail(user, confirmationUrl, subject, locale);
	}

	private void sendMail(GigUser user, String confirmationUrl, String subject, Locale locale) {

		final SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject(subject);
		email.setText(confirmationUrl);
		log.debug("Sending mail : {}", email);
		mailSender.send(email);
	}
}
