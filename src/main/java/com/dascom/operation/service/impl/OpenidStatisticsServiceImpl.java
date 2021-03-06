package com.dascom.operation.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.dascom.operation.entity.OpenidStatisticsParMonth;
import com.dascom.operation.entity.OperationOpenidStatistics;
import com.dascom.operation.service.OpenidStatisticsService;
import com.dascom.operation.utils.AggreationWithResult;
import com.mongodb.DBObject;

@Component("openidStatisticsService")
public class OpenidStatisticsServiceImpl implements OpenidStatisticsService{
	
	@Autowired
	@Qualifier("operationMongoTemplate")
	MongoTemplate operationMongoTemplate;

	@Override
	public Map<String,Object> getOpenidStatistics(int pageNum) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		int pageSize = 10;
		Query query = new Query();
		query.skip((pageNum-1)*pageSize).limit(pageSize);
		//计算总页数
		long sum = operationMongoTemplate.count(query, OperationOpenidStatistics.class);
		int totalPage = (int)(sum%pageSize==0?sum/pageSize:sum/pageSize+1);
		List<OperationOpenidStatistics> dataList = operationMongoTemplate.findAll(OperationOpenidStatistics.class);
		resultMap.put("totalPage", totalPage);
		resultMap.put("data", dataList);
		return resultMap;
	}
	
	private AggreationWithResult result = new AggreationWithResult();

	@Override
	public Map<Object,Object> getOpenidStatisticsPerMonth() {
		Map<Object,Object> resultMap = new HashMap<Object,Object>();
		OpenidStatisticsParMonth openidBean;
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		for(int i=1;i<=month;i++) {
			String pat = i<10?year+"0"+i:year+i+"";
			Aggregation agg = Aggregation.newAggregation(
					Aggregation.match(Criteria.where("statistics_date").regex(pat)),
					Aggregation.group().sum("print_total").as("printTotal")
					.sum("today_openid").as("openidPerMonth")
					.sum("new_openid").as("newOpenidPerMonth")
					);
			
			DBObject obj = result.getResult(agg, operationMongoTemplate, "operation_openid_statistics");
			if(obj!=null) {
				int printTotal = Integer.parseInt(obj.get("printTotal").toString());
				int openidPerMonth = Integer.parseInt(obj.get("openidPerMonth").toString());
				int newOpenidPerMonth = Integer.parseInt(obj.get("newOpenidPerMonth").toString());
				openidBean = new OpenidStatisticsParMonth(printTotal, openidPerMonth, newOpenidPerMonth);
				resultMap.put(pat, openidBean);
			}else {
				resultMap.put(pat, new OpenidStatisticsParMonth(0, 0, 0));
			}
		}
		return resultMap;
	}
	
	
	
}
