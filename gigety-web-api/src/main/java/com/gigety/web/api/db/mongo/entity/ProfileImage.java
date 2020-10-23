package com.gigety.web.api.db.mongo.entity;

import org.bson.types.Binary;
import org.springframework.data.annotation.TypeAlias;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TypeAlias("ProfileImage")
public class ProfileImage {

	private String title;
	private Binary image;
	
}
