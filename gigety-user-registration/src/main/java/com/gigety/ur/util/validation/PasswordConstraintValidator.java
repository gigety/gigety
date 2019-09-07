package com.gigety.ur.util.validation;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

	@Override
	public void initialize(ValidPassword constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value,
			ConstraintValidatorContext context) {
		// @formatter:off
		log.debug("Password::::: {}", value);
		PasswordValidator validator = new PasswordValidator(
				Arrays.asList(
					new LengthRule(8, 30),
					new CharacterRule(EnglishCharacterData.UpperCase, 1),
					new CharacterRule(EnglishCharacterData.LowerCase, 1),
					new CharacterRule(EnglishCharacterData.Digit, 1),
					new CharacterRule(EnglishCharacterData.Special, 1),
					new WhitespaceRule()));
		RuleResult result = validator.validate(new PasswordData(value));

		if (result.isValid()) {
			return true;
		}
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(validator.getMessages(result).toString()).addConstraintViolation();
		// @formatter:on
		return false;
	}

}
