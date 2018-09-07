package com.dascom.operation.service.impl;

import java.util.Calendar;
import java.util.Date;
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

import com.dascom.operation.entity.CollectionPrinterActiveStatistics;
import com.dascom.operation.entity.CollectionPrinters;
import com.dascom.operation.service.CollectionPrintersService;
import com.dascom.operation.utils.AggreationWithResult;
import com.mongodb.DBObject;

@Component("printersService")
public class ColletionPrintersServiceImpl implements CollectionPrintersService {

	@Autowired
	@Qualifier("cloudDeviceMongoTemplate")
	MongoTemplate cloudDeviceMongoTemplate;

	private AggreationWithResult result = new AggreationWithResult();
	private String collectionPrinters = "collection_printers";

	private String printActiveStatistics = "collection_printer_active_statistics";

	@Override
	public int getAllDevice() {
		int total = 0;
		Aggregation agg = Aggregation.newAggregation(Aggregation.group().count().as("总数"));
		DBObject obj = result.getResult(agg, cloudDeviceMongoTemplate, collectionPrinters);

		return Integer.parseInt(obj.get("总数").toString());
	}

	// 统计每日设备上线数
	@Override
	public List<CollectionPrinterActiveStatistics> getOnlineDevice() {
		return cloudDeviceMongoTemplate.findAll(CollectionPrinterActiveStatistics.class);
	}

	@Override
	public Map<String, Integer> getOnlinePerMonth() {
		Map<String, Integer> resultMap = new HashMap<String,Integer>();
		// 获取当前系统的月份
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		for (int i=1;i<=month;i++) {
			String rexg = i<10?year+"0"+i:year+i+"";
			Aggregation agg = Aggregation.newAggregation(
					Aggregation.match(Criteria.where("id").regex(rexg)),
					Aggregation.group().sum("count").as("total")
					);
			DBObject obj = result.getResult(agg, cloudDeviceMongoTemplate, printActiveStatistics);
			int total = obj==null?0:Integer.parseInt(obj.get("total").toString());
			resultMap.put(rexg, total);
		}

		return resultMap;
	}

}
