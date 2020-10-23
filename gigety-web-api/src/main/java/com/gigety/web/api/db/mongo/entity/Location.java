package com.gigety.web.api.db.mongo.entity;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
@TypeAlias("Location")
public class Location {
	private Double lat;
	private Double lng;
}
