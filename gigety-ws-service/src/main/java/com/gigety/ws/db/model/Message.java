package com.gigety.ws.db.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Message {

	@Id
	private String id;
	@Indexed
	private String msgId;
	@Indexed
	private String recipientId;
	@Indexed
	private String senderId;
	private String recipientName;
	private String senderName;
	private String Message;
	private Status status;
}

