package com.gigety.ws.db.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document
@TypeAlias("ChatRoom")
public class ChatRoom {

	@Id
	private String id;
	@Indexed
	private String chatId;
	private String senderId;
	private String recipientId;
}
