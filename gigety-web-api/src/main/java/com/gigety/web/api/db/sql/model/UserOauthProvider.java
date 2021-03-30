package com.gigety.web.api.db.sql.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_oauth_provider")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder()
public class UserOauthProvider {
	
	@EmbeddedId
	private UserOauthProviderIdentity userOauthProviderIdentity;
	private String providerId;
}
