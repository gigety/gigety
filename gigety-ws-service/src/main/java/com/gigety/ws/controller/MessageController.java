package com.gigety.ws.controller;

import java.util.Optional;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.gigety.ws.db.model.Message;
import com.gigety.ws.db.model.MessageNotification;
import com.gigety.ws.service.ChatRoomService;
import com.gigety.ws.service.MessageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MessageController {

	private final ChatRoomService chatRoomService;
	private final MessageService messageService;
	private final SimpMessagingTemplate simMessagingTemplate;
	
	@MessageMapping("/chat")
	public void handleMessage(@Payload Message message) {
		
		Optional<String> msgId = chatRoomService.getChatId(message.getSenderId(), message.getRecipientId(), true);
		message.setMsgId(msgId.get());
		Message savedMessage = messageService.saveMessage(message);
		simMessagingTemplate.convertAndSendToUser(
				message.getRecipientId(), 
				"/queue/messages", 
				MessageNotification.builder()
					.id(savedMessage.getId())
					.senderId(savedMessage.getSenderId())
					.senderName(savedMessage.getSenderName())
					.build()
				);
	}
}
