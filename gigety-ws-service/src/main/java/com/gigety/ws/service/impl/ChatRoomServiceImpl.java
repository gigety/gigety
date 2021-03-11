package com.gigety.ws.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gigety.ws.db.model.ChatRoom;
import com.gigety.ws.db.model.MessageNotification;
import com.gigety.ws.db.repo.ChatRoomRepository;
import com.gigety.ws.service.ChatRoomService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomServiceImpl implements ChatRoomService {

	private final ChatRoomRepository chatRoomRepository;

	@Override
	public Optional<String> getChatId(String senderId, String recipientId, boolean createIfNotExist) {
		
		return chatRoomRepository
				.getChatIdForSenderReciever(senderId, recipientId)
				.map(ChatRoom::getChatId)
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
					log.info("Returning chatId {}",Optional.of(chatId));
					return Optional.of(chatId);
				});
	}

	@Override
	public List<MessageNotification> getNotificationsForRecipient(String recipientId, boolean createIfNotExist) {
		return null;//chatRoomRepository.getChatIdsForRecipient(recipientId);
	}

}
