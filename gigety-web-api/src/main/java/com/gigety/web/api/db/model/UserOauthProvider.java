package com.gigety.web.api.db.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_oauth_provider")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOauthProvider {
	
	@EmbeddedId
	private UserOauthProviderIdentity usertOauthProviderIdentity;
	private String providerId;
}
