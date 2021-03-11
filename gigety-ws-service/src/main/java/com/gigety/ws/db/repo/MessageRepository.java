package com.gigety.ws.db.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gigety.ws.db.model.Message;
import com.gigety.ws.db.model.Status;

public interface MessageRepository extends MongoRepository<Message, String> {

	Long countBySenderIdAndRecipientIdAndStatus(String senderId, String recipientId, Status status);
	List<Message> findByMsgId(String msgId);
	List<Message> findByRecipientId(String recipientId);
	List<Message> findByRecipientIdAndStatus(String recipientId, Status status);
 	Long countByRecipientIdAndStatus(String recipientId, Status status);
}
