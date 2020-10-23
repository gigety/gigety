package com.gigety.web.api.db.mongo.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * UserProfile: Mongo Collection to store user profiles.
 * Indexes are stored create at {@link com.gigety.api.conf.MongoIndexConfiguration}
 * Note: Same Collation must be set on the query as well to take advantage of indexes
 * TODO: This needs to be further tested and confirmed that full collection scans 
 * are avoided and indexes are used at full potential
 * @author samuelsegal
 *
 */
@Data
@Document
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@TypeAlias("UserProfile")
public class UserProfile {

	@Id
	private String id;
	private String title;
	private String description;
	private String email;
	private String userImageUrl;
	private List<ProfileLocation> profileLocations;
	private ProfileImage profileImage;
	@CreatedDate 
	private LocalDateTime createdDate;
	@LastModifiedDate
	private LocalDateTime lastModifiedDate;
	
}
