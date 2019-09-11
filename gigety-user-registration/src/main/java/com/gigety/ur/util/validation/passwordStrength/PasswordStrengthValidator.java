package com.gigety.ur.util.validation.passwordStrength;

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
public class PasswordStrengthValidator implements ConstraintValidator<StrongPassword, String> {

	@Override
	public void initialize(StrongPassword constraintAnnotation) {
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
		StringBuilder errorMessage = new StringBuilder();
		result.getDetails().forEach(detail -> {
			log.debug("Rule Detail :: {}", detail);
			errorMessage.append(detail).append(" -- ");
		});
		//rebuild the error message to specific issues.
		//TODO:Finish this currently it just ads all lerrors
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(errorMessage.toString()).addConstraintViolation();
		// @formatter:on
		return false;
	}

}
