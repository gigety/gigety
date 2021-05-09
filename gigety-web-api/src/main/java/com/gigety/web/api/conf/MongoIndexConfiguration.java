package com.gigety.web.api.conf;

import java.util.Locale;

import org.bson.Document;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.CompoundIndexDefinition;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.index.IndexResolver;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.query.Collation;
import org.springframework.data.mongodb.core.query.Collation.ComparisonLevel;
import org.springframework.stereotype.Component;

import com.gigety.web.api.db.mongo.entity.Contact;
import com.gigety.web.api.db.mongo.entity.Gig;
import com.gigety.web.api.db.mongo.entity.UserProfile;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MongoIndexConfiguration {

	private final MongoTemplate mongoTemplate;
	private final MongoMappingContext mongoMappingContext;
	
	@EventListener(ApplicationReadyEvent.class)
	public void initIndicesAfterStartup() {
		IndexOperations userProfileindexOps = mongoTemplate.indexOps(UserProfile.class);
		IndexResolver userProfileresolver = new MongoPersistentEntityIndexResolver(mongoMappingContext);
		userProfileresolver.resolveIndexFor(UserProfile.class).forEach(up->{
			
			//UserProfile Intersected Indexes(ices)whatever
			//Collation is set to for case insensitive queries on title, description, email
			userProfileindexOps.ensureIndex(new Index("title", Direction.ASC).collation(
					Collation.of(Locale.ENGLISH).strength(ComparisonLevel.secondary())));
			userProfileindexOps.ensureIndex(new Index("description", Direction.ASC).collation(
					Collation.of(Locale.ENGLISH).strength(ComparisonLevel.secondary())));
			userProfileindexOps.ensureIndex(new Index("email", Direction.ASC).collation(
					Collation.of(Locale.ENGLISH).strength(ComparisonLevel.secondary())));
		});
		
		IndexOperations indexOps = mongoTemplate.indexOps(Gig.class);
		IndexResolver resolver = new MongoPersistentEntityIndexResolver(mongoMappingContext);
		resolver.resolveIndexFor(Gig.class).forEach(up->{
			
			//UserProfile Intersected Indexes(ices)whatever
			//Collation is set to for case insensitive queries on title, description, email
			indexOps.ensureIndex(new Index("title", Direction.ASC).collation(
					Collation.of(Locale.ENGLISH).strength(ComparisonLevel.secondary())));
			indexOps.ensureIndex(new Index("description", Direction.ASC).collation(
					Collation.of(Locale.ENGLISH).strength(ComparisonLevel.secondary())));
			indexOps.ensureIndex(new Index("email", Direction.ASC).collation(
					Collation.of(Locale.ENGLISH).strength(ComparisonLevel.secondary())));
		});
		
		IndexOperations uniqueContactsIndexOpS = mongoTemplate.indexOps(Contact.class);
		IndexResolver uniqueContactResolver = new MongoPersistentEntityIndexResolver(mongoMappingContext);
		uniqueContactResolver.resolveIndexFor(Contact.class).forEach(contact -> {
			uniqueContactsIndexOpS.ensureIndex(new CompoundIndexDefinition(new Document().append("userId", 1 ).append("contactId",1 )).unique());
		});
		
	}
}
