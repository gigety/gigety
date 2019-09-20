package com.gigety.ur.db.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "roles_privileges",
		joinColumns = 
		@JoinColumn(
			name="role_id",
			referencedColumnName = "id"),
		inverseJoinColumns = 
		@JoinColumn(
			name="privilege_id",
			referencedColumnName = "id"))
	private Collection<Privilege> privileges;
	
	@NonNull
	private String name;
}
