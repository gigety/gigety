package com.gigety.ur.service;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.util.validation.EmailExistsException;

public interface GigUserService {

	GigUser registerNewUser(GigUser gigUser) throws EmailExistsException;
	GigUser updateExistingUser(GigUser gigUser) throws EmailExistsException;
	
}
