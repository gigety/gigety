package com.gigety.ur.db.model;

import java.util.Calendar;

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

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gigety.ur.util.validation.PasswordMatches;

import lombok.Data;
import lombok.NonNull;

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
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private PWResetToken pwRestToken;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private UserSecurityQuestion userSecurityQuestion;
}
