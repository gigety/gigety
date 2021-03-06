package com.gigety.web.api.db.sql.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Table(
	name = "oauth_provider", 
	uniqueConstraints = {
			@UniqueConstraint(columnNames = "name")
	}
)
@Data
@EqualsAndHashCode(exclude = "users")
@ToString(exclude = "users")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class OauthProvider implements Serializable {

	private static final long serialVersionUID = -1756026587575689594L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NonNull
	@NotNull
	//@NaturalId
	@Size(max = 50)
	private String name;
	
	@ManyToMany(
			fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			},
			mappedBy = "oauthProviders")
	@JsonIgnore
	private Set<User> users;

}




