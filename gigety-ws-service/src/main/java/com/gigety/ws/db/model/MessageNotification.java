package com.gigety.ws.db.model;

import lombok.Builder;
import lombok.Data;

/**
 * Notification so client can be notified that message are available.
 */
@Data
@Builder
public class MessageNotification {
	private String id;
	private String senderId;
	private String senderName;
}
