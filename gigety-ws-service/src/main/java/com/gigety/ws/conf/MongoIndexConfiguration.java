//package com.gigety.ws.conf;
//import java.util.Locale;
//
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.data.domain.Sort.Direction;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.index.Index;
//import org.springframework.data.mongodb.core.index.IndexOperations;
//import org.springframework.data.mongodb.core.index.IndexResolver;
//import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
//import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
//import org.springframework.data.mongodb.core.query.Collation;
//import org.springframework.data.mongodb.core.query.Collation.ComparisonLevel;
//import org.springframework.stereotype.Component;
//
//import com.gigety.web.api.db.mongo.entity.Gig;
//import com.gigety.ws.db.model.ChatRoom;
//import com.gigety.ws.db.model.Message;
//
//import lombok.RequiredArgsConstructor;
//
//@Component
//@RequiredArgsConstructor
//public class MongoIndexConfiguration {
//
//	private final MongoTemplate mongoTemplate;
//	private final MongoMappingContext mongoMappingContext;
//	
//	@EventListener(ApplicationReadyEvent.class)
//	public void initIndicesAfterStartup() {
//		IndexOperations chatRoomIndexOps = mongoTemplate.indexOps(ChatRoom.class);
//		IndexResolver chatRoomResolver = new MongoPersistentEntityIndexResolver(mongoMappingContext);
//		chatRoomResolver.resolveIndexFor(ChatRoom.class).forEach(chatRoom->{
//			
//			//ChatRoom Intersected Indexes(ices)whatever
//			//Collation is set to for case insensitive queries on chatId
//			chatRoomIndexOps.ensureIndex(new Index("chatId", Direction.ASC).collation(
//					Collation.of(Locale.ENGLISH).strength(ComparisonLevel.secondary())));
//		});
//		
//		IndexOperations indexOps = mongoTemplate.indexOps(Message.class);
//		IndexResolver resolver = new MongoPersistentEntityIndexResolver(mongoMappingContext);
//		resolver.resolveIndexFor(Message.class).forEach(message->{
//			
//			//Message Intersected Indexes(ices)whatever
//			//Collation is set to for case insensitive queries on title, description, email
//			indexOps.ensureIndex(new Index("recipientId", Direction.ASC).collation(
//					Collation.of(Locale.ENGLISH).strength(ComparisonLevel.secondary())));
//			indexOps.ensureIndex(new Index("senderId", Direction.ASC).collation(
//					Collation.of(Locale.ENGLISH).strength(ComparisonLevel.secondary())));
//			indexOps.ensureIndex(new Index("email", Direction.ASC).collation(
//					Collation.of(Locale.ENGLISH).strength(ComparisonLevel.secondary())));
//		});
//		
//	}
//}
