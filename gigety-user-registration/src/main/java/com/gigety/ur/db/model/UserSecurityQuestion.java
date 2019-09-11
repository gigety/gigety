package com.gigety.ur.db.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Security Question for given user.
 */
@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class UserSecurityQuestion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.EAGER)
	@NonNull
	private SecurityQuestion question;
	
	//@OneToOne(fetch = FetchType.EAGER, mappedBy = "userSecurityQuestion" ) 
	@OneToOne
	@NonNull
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private GigUser gigUser;
	
	@NonNull
	private String answer;
}
