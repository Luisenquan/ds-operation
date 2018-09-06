package com.dascom.operation.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

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
	private String collectionName = "collection_printers";
	
	//获取当前日期的开始时间
	public static Date getStartTime(){
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY,0+8);
		todayStart.set(Calendar.MINUTE,0);
		todayStart.set(Calendar.SECOND,0);
		todayStart.set(Calendar.MILLISECOND,0);
		return todayStart.getTime();
	}
	
	//获取当前时间的结束时间
	private static Date getEndTime() {
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.set(Calendar.HOUR_OF_DAY,23+8);
		todayEnd.set(Calendar.MINUTE,59);
		todayEnd.set(Calendar.SECOND,59);
		todayEnd.set(Calendar.MILLISECOND,999);
		return todayEnd.getTime();
	}

	
	@Override
	public int getAllDevice() {
		int total = 0;
		Aggregation agg = Aggregation.newAggregation(
				Aggregation.group().count().as("总数")
		);
		DBObject obj = result.getResult(agg, cloudDeviceMongoTemplate, collectionName);

		return Integer.parseInt(obj.get("总数").toString());
	}

	/**
	 * 获取当前日期的设备(登陆过)
	 */
	@Override
	public List<CollectionPrinters> getonlineDevice() {
		Query query = new Query();
		query.addCriteria(Criteria.where("login_date").gte(getStartTime()).lt(getEndTime()));
		return cloudDeviceMongoTemplate.find(query, CollectionPrinters.class);
	}

	
	

}
