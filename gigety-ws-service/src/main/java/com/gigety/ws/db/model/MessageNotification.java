package com.gigety.ws.db.model;

import org.springframework.data.annotation.TypeAlias;

import lombok.Builder;
import lombok.Data;

/**
 * Notification so client can be notified that message are available.
 */
@Data
@Builder
@TypeAlias("MessageNotification")
public class MessageNotification {
	private String id;
	private String senderId;
	private String senderName;
}
