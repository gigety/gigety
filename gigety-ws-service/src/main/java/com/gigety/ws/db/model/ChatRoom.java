package com.gigety.ws.db.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document
public class ChatRoom implements Serializable{

	private static final long serialVersionUID = 8720508259457135594L;
	
	@Id
	private String id;
	@Indexed
	private String chatId;
	private String senderId;
	private String recipientId;
}
