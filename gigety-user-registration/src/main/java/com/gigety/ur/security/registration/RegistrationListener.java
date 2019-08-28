package com.gigety.ur.security.registration;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.service.AsyncEmailService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author samuelsegal
 * User Registration Event Listener
 */
@Component
@Slf4j
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	private final AsyncEmailService emailService;
	
	public RegistrationListener(AsyncEmailService emailService) {
		super();
		this.emailService = emailService;
	}

	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        final GigUser user = event.getUser();
        log.debug("Registration step 1 for {}", user.getEmail());
        emailService.sendRegistrationConfirmationEmail(user, event.getAppUrl(), event.getLocale());
	}

}