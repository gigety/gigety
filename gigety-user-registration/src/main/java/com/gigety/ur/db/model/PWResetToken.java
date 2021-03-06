package com.gigety.ur.db.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Password Rest token. THese tokens are assigned to users who need to reset
 * password via email.
 */
@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class PWResetToken implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NonNull
	private String token;
	private Date expiryDate;

	@OneToOne
	@NonNull
	private GigUser gigUser;

}
