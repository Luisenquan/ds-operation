package com.dascom.operation.service.impl;




import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.dascom.operation.entity.OpenidStatistics;
import com.dascom.operation.service.OpenidInfoService;
import com.dascom.operation.service.OpenidStatisticsService;
import com.dascom.operation.utils.AggreationWithResult;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;



@Component("openidStatisticsService")
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

	@Override
	public void getList() {
		String str = null;
		Aggregation agg = Aggregation.newAggregation(
				Aggregation.unwind("printInfo"),
				Aggregation.match(Criteria.where("printInfo.printDate").is("20180830")),
				Aggregation.group("printInfo.printDate").sum("printInfo.printSucced").as("success").sum("printInfo.printFail").as("fail")
				);
		AggregationResults<BasicDBObject> result = operationMongoTemplate.aggregate(agg,"collection_openid_info",BasicDBObject.class);
		for(Iterator<BasicDBObject> iterator=result.iterator();iterator.hasNext();){
			DBObject obj = iterator.next();
			str = obj.toString();
			}
		System.out.println(str);
		/*AggreationWithResult result = new AggreationWithResult();
		DBObject obj = result.getResult(agg, operationMongoTemplate, "collection_openid_info");
		String str = JSON.toJSONString(obj);
		System.out.println(str);*/
	}

}
