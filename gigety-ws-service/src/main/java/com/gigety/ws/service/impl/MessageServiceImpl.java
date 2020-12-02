package com.gigety.ws.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.gigety.ws.db.model.Message;
import com.gigety.ws.db.model.Status;
import com.gigety.ws.db.repo.MessageRepository;
import com.gigety.ws.exception.ResourceNotFoundException;
import com.gigety.ws.service.ChatRoomService;
import com.gigety.ws.service.MessageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

	private final MessageRepository messageRepository;
	private final ChatRoomService chatRoomService;
	private final MongoOperations mongoOperations;
	
	@Override
	public Message saveMessage(Message message) {
		message.setStatus(Status.RECIEVED);
		return messageRepository.save(message);
		
	}

	@Override
	public Long countNewMessages(String senderId, String recipientId) {
		return messageRepository.countBySenderIdAndRecipientIdAndStatus(
				senderId, recipientId, Status.RECIEVED);
	}

	@Override
	public List<Message> findMessages(String senderId, String recipientId) {
		Optional<String> chatRoomId = chatRoomService.getChatId(senderId, recipientId, false);
	
		List<Message> messages = chatRoomId
				.map(msgId -> messageRepository.findByMsgId(msgId))
				.orElse(new ArrayList<>());
		if(messages.size() > 0) {
			updateStatus(senderId, recipientId, Status.DELIVERED);
		}
		return messages;
	}

	@Override
	public Message findById(String id) {
		return messageRepository
				.findById(id)
				.map(message -> {
					message.setStatus(Status.DELIVERED);
					return messageRepository.save(message);
				}).orElseThrow(()-> new ResourceNotFoundException(
						String.format("No messages Found for id {}", id)));
				
	}

	@Override
	public void updateStatus(String senderId, String recipientId, Status status) {
		Query query = new Query(
				Criteria
					.where("senderId").is(senderId)
					.and("recipientId").is(recipientId));
		Update update = Update.update("status", status);
		mongoOperations.updateMulti(query, update, Message.class);
	}

}
