package com.gigety.web.api.transformer;

import com.gigety.web.api.db.model.User;
import com.gigety.web.api.payload.SignupRequest;

public interface UserConverter {
	User transformSignupRequestToUser(SignupRequest signupRequest);
}
