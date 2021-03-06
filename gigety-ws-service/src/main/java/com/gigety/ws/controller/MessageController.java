package com.gigety.ws.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gigety.ws.db.model.Message;
import com.gigety.ws.db.model.MessageNotification;
import com.gigety.ws.db.model.Status;
import com.gigety.ws.service.ChatRoomService;
import com.gigety.ws.service.MessageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MessageController {

	private final ChatRoomService chatRoomService;
	private final MessageService messageService;
	private final SimpMessagingTemplate simpleMessagingTemplate;
	
	/**
	 * Process a one to one message that is sent. Saves the message to Mongo. 
	 * Also adds the message to the user queue of message broker. 
	 * @param message
	 */
	@MessageMapping("/chat")
	public void handleMessage(@Payload Message message) {
		
		Optional<String> msgId = chatRoomService.getChatId(message.getSenderId(), message.getRecipientId(), true);
		log.info("MsgId ::::::::: {}", msgId.get());
		message.setMsgId(msgId.get());
		message.setStatus(Status.RECEIVED);
		Message savedMessage = messageService.saveMessage(message);
		log.info("Sending Message :: {}", savedMessage);
		simpleMessagingTemplate.convertAndSendToUser(
				message.getRecipientId(), 
				"/queue/messages", 
				MessageNotification.builder()
					.id(savedMessage.getId())
					.senderId(savedMessage.getSenderId())
					.senderName(savedMessage.getSenderName())
					.build()
				);
		log.info("Message Sent to queue {}", message.getRecipientId());
	}
	
	@MessageExceptionHandler({Exception.class})
	@SendTo("/queue/errors")
	public String handleMessageException(Throwable t) {
		String msg = String.format("An error occured in gigety-ws-service handling a message :: %s", t.getMessage());
		log.error(msg, t);
		return msg;
		
	}
	
	@GetMapping("/messages/{senderId}/{recipientId}")
	public ResponseEntity<?> find121ChatMessages( @PathVariable String senderId, @PathVariable String recipientId){
		log.info(String.format("Finding chat message for sender %1$s and recipient %2$s",senderId, recipientId));
		var response = ResponseEntity.ok(messageService.findMessages(senderId, recipientId));
		log.info("Found Messages :: {}", response.getBody());
		return response;
	}
	
	@GetMapping("/messages/{userId}")
	public ResponseEntity<?> findUserMessages(@PathVariable String userId){
		var response = ResponseEntity.ok(messageService.findByRecipientId(userId));
		log.info("Found Message :: {}", response.getBody());
		return response;		
	}
	
	@GetMapping("/messages/status/{status}/{userId}")
	public ResponseEntity<?> findByUserMessagesByStatus(@PathVariable("status") Status status, @PathVariable("userId") String userId){
		return ResponseEntity.ok(messageService.findForUserByStatus(userId, status));
	}
	
}
