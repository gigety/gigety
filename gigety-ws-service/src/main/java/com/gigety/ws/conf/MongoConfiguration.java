package com.gigety.ws.conf;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfiguration extends AbstractMongoClientConfiguration {

	@Value("${spring.data.mongodb.database:gigety}")
	private String database;

	@Value("${spring.data.mongodb.uri}")
	private String uri;

	@Override
	public MongoTemplate mongoTemplate(MongoDatabaseFactory databaseFactory, MappingMongoConverter converter) {

		// remove __class field from mongo
		 converter.setTypeMapper(new DefaultMongoTypeMapper(null));
		 return super.mongoTemplate(databaseFactory, converter);
		//return new MongoTemplate(mongoClient(), database);

	}


	@Override
	public MongoClient mongoClient() {
		MongoClient mongoClient = MongoClients.create(uri);
		
		return mongoClient;

	}

	@Override
	protected String getDatabaseName() {
		// TODO Auto-generated method stub
		return database;
	}
	

}