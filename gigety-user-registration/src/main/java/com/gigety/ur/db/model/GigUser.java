package com.gigety.ur.db.model;

import java.util.Calendar;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.gigety.ur.util.validation.FormValidationGroup;
import com.gigety.ur.util.validation.passwordMatches.PasswordMatches;
import com.gigety.ur.util.validation.passwordStrength.StrongPassword;
import com.gigety.ur.util.validation.securityQuestion.SecurityQuestionAnsweredCorrect;
import com.gigety.ur.util.validation.securityQuestion.SecurityQuestionValidationGroup;

import lombok.Data;

@Entity
@PasswordMatches(groups = FormValidationGroup.class)
@SecurityQuestionAnsweredCorrect(groups = SecurityQuestionValidationGroup.class)
@Data
public class GigUser {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Email
	@NotEmpty(message = "Email required")
	private String email;

	@StrongPassword(groups = FormValidationGroup.class)
	@NotEmpty(message = "Password required")
	private String password;

	
	private Calendar created = Calendar.getInstance();

	private Boolean enabled;


	@Transient
	@NotEmpty(message = "Password confirmation required")
	private String passwordConfirmation;
	
	@Transient
	private String givenAnswer;
	
	@Transient
	private Locale locale;
}
