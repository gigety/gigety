package com.gigety.web.api.service;

import com.gigety.web.api.db.sql.model.User;

public interface UserService {

	User findUserByEmail(String email);
	String findUserImagerUrlById(Long id);
}