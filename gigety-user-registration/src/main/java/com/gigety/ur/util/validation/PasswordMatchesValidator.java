package com.gigety.ur.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.gigety.ur.db.model.GigUser;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object>{

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		final GigUser user = (GigUser)value;
		return user.getPassword().equals(user.getPasswordConfirmation());
	}

	@Override
	public void initialize(PasswordMatches constraintAnnotation) {
		// TODO Auto-generated method stub
		//ConstraintValidator.super.initialize(constraintAnnotation);
	}

	
}
