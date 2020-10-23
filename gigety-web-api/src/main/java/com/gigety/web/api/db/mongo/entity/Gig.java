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

@Data
@Document
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@TypeAlias("Gig")
public class Gig {
	@Id
	private String id;
	private String title;
	private String description;
	private String email;
	private String userImageUrl;
	private List<GigLocation> gigLocation;
	@CreatedDate 
	private LocalDateTime createdDate;
	@LastModifiedDate
	private LocalDateTime lastModifiedDate;

}
