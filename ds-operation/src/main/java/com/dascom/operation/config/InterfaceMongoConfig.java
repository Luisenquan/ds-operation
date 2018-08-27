package com.dascom.operation.config;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.MongoClientURI;

/**
 * db:interface
 * @author Leisenquan
 * @date 2018年8月21日
 * @project_name ds-operation
 */
@Configuration
public class InterfaceMongoConfig {
	
	@Bean
	@ConfigurationProperties(prefix="spring.data.interface.mongodb")
	public MongoProperties interfaceMongoProperties(){
		return new MongoProperties();
	}
	
	@Bean(name="interfaceMongoTemplate")
	public MongoTemplate interfaceMongoTemplate() throws Exception{
		MongoDbFactory factory = interfaceFactory(interfaceMongoProperties());
		MappingMongoConverter converter = new MappingMongoConverter(factory,  new MongoMappingContext());
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));
		return new MongoTemplate(factory,converter);
	}
	
	@Bean
	public MongoDbFactory interfaceFactory(MongoProperties mongoProperties) throws Exception{
		MongoProperties mongoProperties1 = interfaceMongoProperties();
		return new SimpleMongoDbFactory(new MongoClientURI(mongoProperties1.getUri()));
	}

}
