package com.gigety.web.api.conf.db.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gigety.web.api.util.AuthProvider;

import lombok.Data;

@Entity
@Table(
	name = "users", 
	uniqueConstraints = 
	{
		@UniqueConstraint(columnNames = "email")
	}
)
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Email
	@Column(nullable = false)
	private String email;
	private String imageUrl;
	
	@Column(nullable = false)
	private Boolean emailVerified = false;
	
	@JsonIgnore
	private String password;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private AuthProvider provider;
	private String providerId;
}
