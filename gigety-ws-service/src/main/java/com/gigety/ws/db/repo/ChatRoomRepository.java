package com.gigety.ws.db.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.gigety.ws.db.model.ChatRoom;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {

	@Query(value = "{senderId: ?0, recipientId: ?1}, ", fields= "{chatId: 1}") //Projection
	Optional<ChatRoom> getChatIdForSenderReciever(String senderId, String recipientId);
}
