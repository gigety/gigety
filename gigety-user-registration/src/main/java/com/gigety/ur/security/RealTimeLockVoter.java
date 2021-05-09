package com.gigety.ur.security;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.gigety.ur.service.cached.LockedUserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RealTimeLockVoter implements AccessDecisionVoter<Object> {

	private final LockedUserService lockedUserService;
	
	
	public RealTimeLockVoter(LockedUserService lockedUserService) {
		super();
		this.lockedUserService = lockedUserService;
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int vote(Authentication authentication,
			Object object,
			Collection<ConfigAttribute> attributes) {
		log.debug("{} is locked = {}", authentication.getName(),
				lockedUserService.isUserLocked(authentication.getName()));
		if (lockedUserService.isUserLocked(authentication.getName())) {
			return ACCESS_DENIED;
		}
		return ACCESS_GRANTED;
	}

}
