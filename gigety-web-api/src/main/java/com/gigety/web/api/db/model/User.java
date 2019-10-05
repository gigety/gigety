package com.gigety.web.api.db.model;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gigety.web.api.util.AuthProvider;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(
	name = "users", 
	uniqueConstraints = 
	{
		@UniqueConstraint(columnNames = "email")
	}
)
@Data
@EqualsAndHashCode(exclude = "oauthProviders")
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

	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			})
	@JoinTable(name = "user_oauth_provider",
			joinColumns = {@JoinColumn(name  = "user_id")},
			inverseJoinColumns = {@JoinColumn(name = "oauth_provider_id")})
	@JsonIgnore
	private Set<OauthProvider> oauthProviders = new HashSet<>();
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private AuthProvider provider;
	private String providerId;
}
