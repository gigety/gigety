package com.gigety.ur.security.registration;

import org.springframework.context.ApplicationEvent;

import com.gigety.ur.db.model.GigUser;

import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class OnRegistrationCompleteEvent extends ApplicationEvent {

	private final String appUrl;
	private final GigUser user;
	
	public OnRegistrationCompleteEvent(GigUser user, String appUrl) {
		super(user);
		this.user = user;
		this.appUrl = appUrl;
	}

}
