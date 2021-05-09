package com.gigety.web.api.db.mongo.entity;

import java.util.List;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@Document
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@TypeAlias("UserAccount")
public class UserAccount {

	@Indexed(unique = true)
	private String mysqlUserId;
	@Indexed(background = true)
	private String email;
	private String userName;
	private String imageUrl;
	private UserProfile activeProfile;
	private Contact activeContact;
	

	// @Transient
	private List<UserProfile> allProfiles;
	private List<Contact> allContacts;
}
