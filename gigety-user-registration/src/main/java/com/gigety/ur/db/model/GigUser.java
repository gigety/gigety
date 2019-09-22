package com.gigety.ur.db.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.gigety.ur.util.validation.PasswordValidationGroup;
import com.gigety.ur.util.validation.passwordMatches.PasswordMatches;
import com.gigety.ur.util.validation.passwordStrength.StrongPassword;
import com.gigety.ur.util.validation.securityQuestion.SecurityQuestionAnsweredCorrect;
import com.gigety.ur.util.validation.securityQuestion.SecurityQuestionValidationGroup;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@PasswordMatches(groups = PasswordValidationGroup.class)
@SecurityQuestionAnsweredCorrect(groups = SecurityQuestionValidationGroup.class)
@Data
@NoArgsConstructor
public class GigUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Email
	@NotEmpty(message = "Email required")
	private String email;

	@StrongPassword(groups = PasswordValidationGroup.class)
	@NotEmpty(message = "Password required")
	private String password;
	
	private Calendar created = Calendar.getInstance();
	private Boolean enabled;
	private Boolean locked;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles",
			joinColumns = 
			@JoinColumn(
					name = "user_id",
					referencedColumnName = "id"),
			inverseJoinColumns = 
			@JoinColumn(
					name="role_id",
					referencedColumnName = "id"))
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Collection<Role> roles;
	
	@Transient
	@NotEmpty(groups = PasswordValidationGroup.class, message = "Password confirmation required")
	private String passwordConfirmation;
	
	@Transient
	private String givenAnswer;
	
	@Transient
	private Locale locale;
}
