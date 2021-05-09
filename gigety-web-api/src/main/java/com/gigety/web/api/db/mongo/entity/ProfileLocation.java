package com.gigety.web.api.db.mongo.entity;

import javax.persistence.Id;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

@Data
@Document
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@TypeAlias("ProfileLocation")
public class ProfileLocation {

	@Id
	@JsonSerialize(using = ToStringSerializer.class )
	private ObjectId id = new ObjectId();
	private String address;
	private Location location;
	private String radius;
	
}
