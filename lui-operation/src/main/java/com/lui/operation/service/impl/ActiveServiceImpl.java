package com.lui.operation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.lui.operation.entity.Active;
import com.lui.operation.service.ActiveService;

@Component("activeService")
public class ActiveServiceImpl implements ActiveService{
	
	@Autowired
	private MongoTemplate template;

	@Override
	public List<Active> fetchActive() {
		Query query = new Query();
		query.skip(0);
		query.limit(30);
		return template.find(query, Active.class);
	}

}
