package com.gigety.ur.util.validation;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

	@Override
	public void initialize(ValidPassword constraintAnnotation) {
		// TODO Auto-generated method stub
		//ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(String value,
			ConstraintValidatorContext context) {
		log.debug("Password::::: {}", value);
		PasswordValidator validator = new PasswordValidator(Arrays.asList(new LengthRule(8, 30)));
		RuleResult result = validator.validate(new PasswordData(value));

		if (result.isValid()) {
			return true;
		}
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(validator.getMessages(result).toString()).addConstraintViolation();
		return false;
	}

}
