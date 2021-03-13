package com.gigety.ws.db.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Message implements Serializable{

	private static final long serialVersionUID = -1084754918635396101L;
	
	@Id
	private String id;
	@Indexed
	private String msgId;
	@Indexed
	private String recipientId;
	@Indexed
	private String senderId;
	//TODO: consider extracting senderId and senderName to contact field or add a contact field 
	//private Contact contact;
	private String recipientName;
	private String senderName;
	private String content;
	private Date timestamp;
	private Status status;
}

