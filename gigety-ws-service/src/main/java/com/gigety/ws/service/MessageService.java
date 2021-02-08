package com.gigety.ws.service;

import java.util.List;

import com.gigety.ws.db.model.Message;
import com.gigety.ws.db.model.Status;

public interface MessageService {

	Message saveMessage(Message message);

	Long countNewMessages(String senderId, String recipientId);

	Long countNewMessages(String userId);

	List<Message> findMessages(String senderId, String recipientId);

	List<Message> findForUserByStatus(String userId, Status status);

	Message findById(String id);

	Message findByRecipientId(String recipientId);

	void updateStatus(String senderId, String recipientId, Status status);
}
