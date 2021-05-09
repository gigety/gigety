package com.gigety.ws.service;

import java.util.List;
import java.util.Optional;

import com.gigety.ws.db.model.MessageNotification;

public interface ChatRoomService {

	Optional<String> getChatId(String senderId, String recipientId, boolean createIfNotExist);
	List<MessageNotification> getNotificationsForRecipient(String recipientId, boolean createIfNotExist);
}
