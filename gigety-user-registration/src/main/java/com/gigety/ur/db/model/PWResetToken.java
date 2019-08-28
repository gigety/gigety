package com.gigety.ur.db.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NonNull;

@Entity
@Data
public class PWResetToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NonNull
	private String token;
	private Date expiryDate;
	
	@OneToOne(targetEntity = GigUser.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	@NonNull
	private GigUser gigUser;

}
