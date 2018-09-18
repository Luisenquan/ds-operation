package com.lui.operation.config;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.MongoClientURI;

@Configuration
public class DocumentMongoConfig {
	
	@Bean
	@Primary
	@ConfigurationProperties(prefix="spring.data.document.mongodb")
	public MongoProperties operationMongoProperties(){
		return new MongoProperties();
	}
	
	@Bean(name="documentMongoTemplate")
	public MongoTemplate operationMongoTemplate() throws Exception{
		MongoDbFactory factory = operationFactory(operationMongoProperties());
		MappingMongoConverter converter = new MappingMongoConverter(factory,  new MongoMappingContext());
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));
		return new MongoTemplate(factory,converter);
	}
	
	@Bean
	public MongoDbFactory operationFactory(MongoProperties mongoProperties) throws Exception{
		MongoProperties mongoProperties1 = operationMongoProperties();
		return new SimpleMongoDbFactory(new MongoClientURI(mongoProperties1.getUri()));
	}

}
