package com.gigety.ur.util.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.springframework.validation.annotation.Validated;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.web.controller.RegistrationController;


/**
 * ValidPassword - annotation validated via {@link PasswordConstraintValidator}
 * Due to password encoding, this should only be used on non-encoded passwords.
 * THis requires a groups setting on the annotation to be set, so the 
 * validation is not performed while saving to database when passwords are 
 * encoded. {@link FormValidationGroup} was created for this purpose.
 * @see {@link GigUser} - @ValidPassword(groups = FormValidationGroup.class) 
 * @see {@link RegistrationController} - @Validated(FormValidationGroup.class) 
 */
@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

	String message() default "Invalid Password";
	Class<?>[] groups() default{};
	Class<? extends Payload>[] payload() default{};
}
