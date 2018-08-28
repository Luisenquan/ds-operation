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
	@Qualifier("operationMongoTemplate")
	MongoTemplate operationMongoTemplate;


	public List<CollectionInterface> getAllInterface() {
		
		return operationMongoTemplate.findAll(CollectionInterface.class);
	}


	@Override
	public void addInterface(CollectionInterface inter) {
		operationMongoTemplate.insert(inter);
	}

}
