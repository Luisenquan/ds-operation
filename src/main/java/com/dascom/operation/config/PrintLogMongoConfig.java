package com.dascom.operation.config;

import java.net.UnknownHostException;

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

/**
 * db:printLog 
 * @author Leisenquan
 * @date 2018年8月21日
 * @project_name ds-operation
 */
@Configuration
public class PrintLogMongoConfig {
	@Bean
	@Primary
	@ConfigurationProperties(prefix="spring.data.print_log.mongodb")
	public MongoProperties printLogMongoProperties(){
		return new MongoProperties();
	}
	
	@Bean(name="printLogMongoTemplate")
	@Primary
	public MongoTemplate printLogMongoTemplate() throws Exception{
		MongoDbFactory factory = printLogFactory(printLogMongoProperties());
		MappingMongoConverter converter = new MappingMongoConverter(factory,  new MongoMappingContext());
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));
		return new MongoTemplate(factory,converter);
	}
	
	@Bean
	@Primary
	public MongoDbFactory printLogFactory(MongoProperties mongoProperties) throws Exception{
		MongoProperties mongoProperties1 = printLogMongoProperties();
		return new SimpleMongoDbFactory(new MongoClientURI(mongoProperties1.getUri()));
	}
	

}
