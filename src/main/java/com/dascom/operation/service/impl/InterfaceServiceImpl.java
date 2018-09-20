package com.dascom.operation.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
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
