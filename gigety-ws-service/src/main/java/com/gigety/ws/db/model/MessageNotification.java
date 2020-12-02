package com.gigety.ws.db.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

/**
 * Notification so client can be notified that message are available.
 */
@Data
@Document
@Builder
public class MessageNotification {
	private String id;
	private String senderId;
	private String senderName;
}
