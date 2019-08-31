package com.gigety.ur.db.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author samuelsegal
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
	
	@OneToOne
	@JoinColumn()
	@NonNull
	private SecurityQuestion question;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "userSecurityQuestion", orphanRemoval = true) 
	@NonNull
	private GigUser gigUser;
	
	@NonNull
	private String answer;
}
