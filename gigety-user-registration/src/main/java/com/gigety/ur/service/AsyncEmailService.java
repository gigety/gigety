package com.gigety.ur.service;

import java.util.Locale;

import org.springframework.stereotype.Service;

import com.gigety.ur.db.model.GigUser;

@Service
public interface AsyncEmailService {
	
	void sendRegistrationConfirmationEmail(GigUser gigUser, String appUrl, Locale locale);
	void sendPWResetEmail(GigUser gigUser, String appUrl, Locale locale);
	
}
