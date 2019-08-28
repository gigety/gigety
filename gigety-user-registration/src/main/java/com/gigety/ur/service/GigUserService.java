package com.gigety.ur.service;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.db.model.VerificationToken;
import com.gigety.ur.util.validation.EmailExistsException;

public interface GigUserService {

	GigUser registerNewUser(GigUser gigUser) throws EmailExistsException;
	GigUser updateExistingUser(GigUser gigUser) throws EmailExistsException;
	void assignVerificationToken(GigUser gigUser, String token);
	void assignResetPWToken(GigUser gigUser, String token);
	VerificationToken getVerificationToken(String token);
	GigUser saveRegisteredUser(GigUser gigUser);
	GigUser findByEmail(String email);
}
