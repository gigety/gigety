package com.gigety.web.api.service;

import com.gigety.web.api.db.model.User;

public interface UserService {

	User findUserByEmail(String email);
}