package com.dascom.operation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.dascom.operation.entity.OpenidStatistics;
import com.dascom.operation.service.OpenidStatisticsService;
import com.dascom.operation.utils.AggreationWithResult;
import com.mongodb.DBObject;

@Component("openidStatisticsService")
public class OpenidStatisticsServiceImpl implements OpenidStatisticsService {
	
	@Autowired
	@Qualifier("operationMongoTemplate")
	MongoTemplate operationMongoTemplate;

	private AggreationWithResult result = new AggreationWithResult();

	// 插入统计数据
	@Override
	public void insertStatistics() {
		OpenidStatistics statistics;
		// 统计当天所有的打印次数
		Aggregation agg = Aggregation.newAggregation(Aggregation.unwind("print_info"),
				Aggregation.match(Criteria.where("print_info.print_date").is("20180830")),
				Aggregation.group("print_info.print_date").sum("print_info.print_succed").as("success")
						.sum("print_info.print_fail").as("fail"));
		
		DBObject obj = result.getResult(agg, operationMongoTemplate, "collection_openid_info");
		String statisticsDate = obj.get("_id").toString();
		int statisticsSuccess = Integer.parseInt(obj.get("success").toString());
		int statisticsFail = Integer.parseInt(obj.get("fail").toString());
		//打印总数
		int printTotal = statisticsSuccess+statisticsFail;
		
		
		//统计openid总和
		Aggregation aggTotal = Aggregation.newAggregation(Aggregation.group().count().as("totalByDay"));
		DBObject totalObj = result.getResult(aggTotal, operationMongoTemplate, "collection_openid_info");
		int totalOpenid = Integer.parseInt(totalObj.get("totalByDay").toString());
		
		
		//统计新增的openid
		Aggregation aggNewOpenid = Aggregation.newAggregation(
				Aggregation.match(Criteria.where("first_using").is("20180830")),
				Aggregation.group().count().as("newByDay")
				);
		DBObject newOpendiObj = result.getResult(aggNewOpenid, operationMongoTemplate, "collection_openid_info");
		int newOpenid = Integer.parseInt(newOpendiObj.get("newByDay").toString());
		
		
		
		statistics = new OpenidStatistics(statisticsDate, statisticsSuccess, statisticsFail, printTotal,totalOpenid,newOpenid);
		operationMongoTemplate.insert(statistics);
	}

	@Override
	public List<OpenidStatistics> getOpenidStatisticsList() {
		return operationMongoTemplate.findAll(OpenidStatistics.class);
	}

}
