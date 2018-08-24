package com.dascom.operation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.dascom.operation.entity.CollectionInterface;
import com.dascom.operation.service.InterfaceService;

@Component("interfaceService")
public class InterfaceServiceImpl implements InterfaceService{
	
	@Autowired
	@Qualifier("interfaceMongoTemplate")
	MongoTemplate interfaceMongoTemplate;


	public List<CollectionInterface> getAllInterface() {
		
		return interfaceMongoTemplate.findAll(CollectionInterface.class);
	}


	@Override
	public void addInterface(CollectionInterface inter) {
		interfaceMongoTemplate.insert(inter);
	}

}
