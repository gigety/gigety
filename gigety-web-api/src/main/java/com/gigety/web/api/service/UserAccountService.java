package com.gigety.web.api.service;

import com.gigety.web.api.db.mongo.entity.Contact;
import com.gigety.web.api.db.mongo.entity.UserAccount;
import com.gigety.web.api.exception.ResourceNotFoundException;

public interface UserAccountService {
	UserAccount getUserAccount(String email) throws ResourceNotFoundException;
	UserAccount createUserAccount(UserAccount userAccount);
	UserAccount updateUserAccount(UserAccount userAccount);
	UserAccount setActiveContact(String mysqlUserId, Contact contact);
}
