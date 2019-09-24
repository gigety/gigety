package com.gigety.ur.service.cached;

import com.gigety.ur.db.model.LockedUser;

public interface LockedUserService {
	LockedUser lockUser(String email, String enforcerEmail);
	void unlockUser(String email);
	boolean isUserLocked(String email);
}
