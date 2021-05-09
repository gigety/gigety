package com.gigety.ur.security.registration;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.gigety.ur.db.model.GigUser;

import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class OnRegistrationCompleteEvent extends ApplicationEvent {

	private final String appUrl;
	private final GigUser user;
	private final Locale locale;
	public OnRegistrationCompleteEvent(GigUser user, String appUrl, Locale locale) {
		super(user);
		this.user = user;
		this.appUrl = appUrl;
		this.locale = locale;
	}
	

}
