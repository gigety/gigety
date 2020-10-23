package com.gigety.ur.util.validation.securityQuestion;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.db.model.UserSecurityQuestion;
import com.gigety.ur.service.SecurityQuestionService;

import lombok.extern.slf4j.Slf4j;

/**
 * Validate that a users security question is answer matches wqhat is store in
 * db for given user. To be used when users must answer there question such as
 * on the password reset page. Controller methods should include
 * @Validated(SecurityQuestionValidationGroup.class)
 * @see SecurityQuestionAnsweredCorrect
 */
@Slf4j
public class SecurityQuestionValidator implements ConstraintValidator<SecurityQuestionAnsweredCorrect, Object>{

	private final SecurityQuestionService securityQuestionService;
	private final MessageSource msgSource;
	

	public SecurityQuestionValidator(SecurityQuestionService securityQuestionService, MessageSource msgSrouce) {
		super();
		this.securityQuestionService = securityQuestionService;
		this.msgSource = msgSrouce;
	}

	@Override
	public boolean isValid(Object value,
			ConstraintValidatorContext context) {
		final GigUser user = (GigUser)value;
		final String givenAnswer = user.getGivenAnswer();
		UserSecurityQuestion userAnswer = securityQuestionService.findByUser(user);
		final String savedAnswer = userAnswer.getAnswer();
		if(givenAnswer.equals(savedAnswer)) {
			log.debug("SECURITY QUESTION ANSWER CORRECT :: {}", givenAnswer);
			return true;
		}
		log.warn("SECURITY QUESTION ANSWER INCORRECT :: {} :: Should BE ->   :: {}",givenAnswer, savedAnswer);
		if(user.getLocale() == null) {
			log.warn("User is missing locale SETTING TO DEFAULT:: {}",user);
			user.setLocale(LocaleContextHolder.getLocale());
		}
		
		//Internationalize z message
		String msg = msgSource.getMessage("answer.incorrect", null, user.getLocale());
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
		return false;
	}

	@Override
	public void initialize(SecurityQuestionAnsweredCorrect constraintAnnotation) {
		// TODO Auto-generated method stub
		//ConstraintValidator.super.initialize(constraintAnnotation);
	}

	
}
