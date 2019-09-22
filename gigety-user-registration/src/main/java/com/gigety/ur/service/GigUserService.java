package com.gigety.ur.service;

import java.util.List;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.db.model.PWResetToken;
import com.gigety.ur.db.model.UserSecurityQuestion;
import com.gigety.ur.db.model.VerificationToken;
import com.gigety.ur.util.validation.EmailExistsException;

public interface GigUserService {

	GigUser updateExistingUser(GigUser gigUser) throws EmailExistsException;
	void assignVerificationToken(GigUser gigUser, String token);
	void assignResetPWToken(GigUser gigUser, String token);
	VerificationToken getVerificationToken(String token);
	PWResetToken getPWRestToken(String token);
	GigUser saveRegisteredUser(GigUser gigUser);
	GigUser findByEmail(String email);
	boolean changePassword(GigUser gigUser, String password);
	void removeUser(Long id);
	Iterable<GigUser> findAll();
	List<GigUser> findByEmails(List<String> emails);
	GigUser registerNewUser(GigUser user,
			UserSecurityQuestion userSecurityQuestion) throws EmailExistsException, Exception;
	void lockUser(Long id, boolean lock);
	GigUser findById(Long id);
}