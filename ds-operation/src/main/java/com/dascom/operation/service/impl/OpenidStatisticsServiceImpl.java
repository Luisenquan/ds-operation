package com.dascom.operation.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.dascom.operation.entity.OpenidStatistics;
import com.dascom.operation.service.OpenidInfoService;
import com.dascom.operation.service.OpenidStatisticsService;

public class OpenidStatisticsServiceImpl implements OpenidStatisticsService{
	
	@Autowired
	private OpenidInfoService openidInfoService;
	
	@Autowired
	@Qualifier("operationMongoTemplate")
	MongoTemplate operationMongoTemplate;

	@Override
	public void insert() {
		int totalOpenid = openidInfoService.fetchByNowDay().size();
		String statisticsDate = null;
		int statisticsSuccess = 0;
		int statisticsFail = 0;
		OpenidStatistics openidStatistics = new OpenidStatistics(statisticsDate, statisticsSuccess, statisticsFail, totalOpenid);
		
		operationMongoTemplate.insert(openidStatistics);
	}

}
