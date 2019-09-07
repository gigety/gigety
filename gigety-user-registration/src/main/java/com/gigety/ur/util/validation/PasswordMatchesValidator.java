package com.gigety.ur.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.gigety.ur.db.model.GigUser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object>{

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		final GigUser user = (GigUser)value;
		log.debug("PW1: {}", user.getPassword());		
		log.debug("PW2: {}", user.getPasswordConfirmation());
		
		boolean matches =  user.getPassword().equals(user.getPasswordConfirmation());
		log.debug("Password matches : {}",matches);
		return matches;
	}

	@Override
	public void initialize(PasswordMatches constraintAnnotation) {
		// TODO Auto-generated method stub
		//ConstraintValidator.super.initialize(constraintAnnotation);
		
	}

	
}
