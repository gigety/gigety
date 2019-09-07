package com.gigety.ur.util.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * PasswordMatches - annotation for {@link PasswordMatchesValidator}
 * Used with {@link FormValidationGroup} for groups options.
 * @PasswordMatches(groups = FormValidationGroup.class)
 * now only valid annotations with this groups set will trigger validation.
 * i.e. @Validated(FormValidationGroup.class) 
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.TYPE_PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {

	String message() default "Password no matchy";
	Class<?>[] groups() default{};
	Class<? extends Payload>[] payload() default {};
}
