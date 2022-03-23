package com.fleaMarket.fleaMarketBackend.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {
	
	@Value("${database.name}")
	private String databaseName;

	@Override
	protected String getDatabaseName() {
		return databaseName;
	}

	@Override
	public MongoClient mongoClient() {
		String databaseName = getDatabaseName();
		ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/" + databaseName);
		MongoClientSettings mongoClientSetting = MongoClientSettings
												.builder()
												.applyConnectionString(connectionString)
												.build();
		
		return MongoClients.create(mongoClientSetting);
	}
	
	@Override
	protected Collection<String> getMappingBasePackages() {
		return Collections.singleton("com.fleaMarket.fleaMarketBackend");
	}
	
	@Bean
	public ValidatingMongoEventListener validatingMongoEventListener() {
		return new ValidatingMongoEventListener(validator());
	}
	
	@Bean
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}
}
