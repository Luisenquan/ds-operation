package com.dascom.operation.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dascom.operation.entity.OpenidStatistics;
import com.dascom.operation.service.OpenidStatisticsService;


@Component("openidService")
public class OpenidStatisticsServiceImpl implements OpenidStatisticsService{
	
	@Autowired
	@Qualifier("operationMongoTemplate")
	MongoTemplate operationMongoTemplate;

	@Override
	public List<OpenidStatistics> getAll() {
		return operationMongoTemplate.findAll(OpenidStatistics.class);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkOpenid(String openid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("openid").is(openid));
		OpenidStatistics  result = operationMongoTemplate.findOne(query, OpenidStatistics.class);
		if(!StringUtils.isEmpty(result)) {
			return true;
		}
		return false;
	}

}
