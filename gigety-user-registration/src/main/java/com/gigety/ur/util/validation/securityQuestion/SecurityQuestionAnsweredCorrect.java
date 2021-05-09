package com.gigety.ur.util.validation.securityQuestion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.gigety.ur.db.model.GigUser;

/**
 * Annotation for GigUser at class level to validate that the saved users 
 * security question answer is same as givenAnswer.
 * example --
 *  @SecurityQuestionAnsweredCorrect(groups=SecurityQuestionValidationGroup.class)
 * @see GigUser
 * @see SecurityQuestionValidationGroup
 * @see SecurityQuestionValidator
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.TYPE_PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SecurityQuestionValidator.class)
@Documented
public @interface SecurityQuestionAnsweredCorrect {
	String message() default "Answer to question is incorrect - Internationlize this Samo";
	Class<?>[] groups() default{};
	Class<? extends Payload>[] payload() default {};
}
