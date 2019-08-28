package com.gigety.ur.db.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.gigety.ur.util.validation.PasswordMatches;

import lombok.Data;

@Entity
@PasswordMatches
@Data
public class GigUser {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Email
	@NotEmpty(message = "Email required")
	private String email;

	@NotEmpty(message = "Password required")
	private String password;
	
	@Transient
	@NotEmpty(message = "Password confirmation required")
	private String passwordConfirmation;
	
	private Calendar created = Calendar.getInstance();

	private Boolean enabled;
}
