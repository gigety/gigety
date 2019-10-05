package com.gigety.web.api.db.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOauthProviderIdentity implements Serializable{

	private static final long serialVersionUID = -497103936240411157L;
	
	@NotNull
	@Column(name = "user_id")
	private Long userId;
	@NotNull
	@Column(name = "oauth_provider_id")
	private Long oauthProviderId;
	
}
