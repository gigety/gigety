package com.gigety.ur.service.cached;

import com.gigety.ur.db.model.LockedUser;

public interface LockedUserService {
	LockedUser lockUser(Long lockedUserId, String enforcerEmail);
	void unlockUser(Long lockedUserId);
	boolean isUserLocked(String email);
}
