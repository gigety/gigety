package com.gigety.ws.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gigety.ws.db.model.ChatRoom;
import com.gigety.ws.db.repo.ChatRoomRepository;
import com.gigety.ws.service.ChatRoomService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

	private final ChatRoomRepository chatRoomRepository;

	@Override
	public Optional<String> getChatId(String senderId, String recipientId, boolean createIfNotExist) {
		
		return chatRoomRepository
				.getChatIdForSenderReciever(senderId, recipientId)
				.or(() -> {
					if(!createIfNotExist) {
						return Optional.empty();
					}
					String chatId = String.format("%s_%s", senderId, recipientId);
					ChatRoom sr = ChatRoom.builder()
							.chatId(chatId)
							.senderId(senderId)
							.recipientId(recipientId)
							.build();
					ChatRoom rs = ChatRoom.builder()
							.chatId(chatId)
							.senderId(recipientId)
							.recipientId(senderId)
							.build();
					chatRoomRepository.save(sr);
					chatRoomRepository.save(rs);
					
					return Optional.of(chatId);
				});
	}

}
