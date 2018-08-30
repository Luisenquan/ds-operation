package com.dascom.operation.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.dascom.operation.entity.PrintStatistics;
import com.dascom.operation.service.PrintStatisticeService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;


@Component("printStatisticeService")
public class PrintStatisticeServiceImpl implements PrintStatisticeService{
	
	
	@Autowired
	@Qualifier("printLogMongoTemplate")
	MongoTemplate printLogMongoTemplate;

	@Override
	public List<PrintStatistics> getAll() {
		return printLogMongoTemplate.findAll(PrintStatistics.class);
	}
	
	private DBObject getResult(Aggregation agg){
		DBObject obj = null;
		AggregationResults<BasicDBObject> result = printLogMongoTemplate.aggregate(agg, "collection_print_statistics",BasicDBObject.class);
		for(Iterator<BasicDBObject> iterator=result.iterator();iterator.hasNext();){
			obj = iterator.next();
		}
		return obj;
	}

	
	/**
	 * 按月份模糊查询
	 */
	@Override
	public List<PrintStatistics> getMonth(String date) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("id").regex(date));
		return printLogMongoTemplate.find(query, PrintStatistics.class);
	}


	
	/**
	 * 查询各设备使用次数
	 */
	@Override
	public List<String> getPrintTotal() {
		String result_info = null;
		List<String> resultList = new ArrayList<String>();
		Aggregation agg = Aggregation.newAggregation(Aggregation.group("number").count().as("used_times"));
		AggregationResults<BasicDBObject> result = 
				printLogMongoTemplate.aggregate(agg,"collection_print_statistics" ,BasicDBObject.class);
		for(Iterator<BasicDBObject> iterator=result.iterator();iterator.hasNext();){
			DBObject obj = iterator.next();
			result_info = JSON.toJSONString(obj);
			resultList.add(result_info);
		}
		return resultList;
	}


	/**
	 * 计算每台设备的成功打印次数
	 */
	public String totalSucceedTimes(String number) {
		String total = null;
		Aggregation agg = Aggregation.newAggregation(
				Aggregation.match(Criteria.where("number").is(number)),
				Aggregation.group("number").sum("succeed_page").as("success_times").sum("failure_page").as("failure_times")
		);
		AggregationResults<BasicDBObject> result = 
				printLogMongoTemplate.aggregate(agg,"collection_print_statistics" ,BasicDBObject.class);
		for(Iterator<BasicDBObject> iterator = result.iterator();iterator.hasNext();){
			DBObject obj = iterator.next();
			total = JSON.toJSONString(obj);
		}
		return total;
	}


	/**
	 * 统计所有设备的总打印数
	 */
	@Override
	public int totalPrintTimes() {
		int total = 0;
		Aggregation agg = Aggregation.newAggregation(
				Aggregation.group().sum("succeed_page").as("success_times").sum("failure_page").as("failure_tiems")
		);
		AggregationResults<BasicDBObject> result = printLogMongoTemplate.aggregate(agg, "collection_print_statistics",BasicDBObject.class);
		for(Iterator<BasicDBObject> iterator = result.iterator();iterator.hasNext();){
			DBObject obj = iterator.next();
			int successTimes = Integer.parseInt(obj.get("success_times").toString());
			int failureTimes = Integer.parseInt(obj.get("failure_tiems").toString());
			total= successTimes + failureTimes;
		}
		return total;
	}


	/**
	 * 按月统计设备的打印情况
	 */
	@Override
	public String totalPrintWithMonth(String date) {
		String printInfo = null;
		Aggregation agg = Aggregation.newAggregation(
				Aggregation.match(Criteria.where("id").regex(date)),
				Aggregation.group().sum("succeed_page").as("success_times").sum("failure_page").as("failure_tiems")
		);
		AggregationResults<BasicDBObject> result = printLogMongoTemplate.aggregate(agg, "collection_print_statistics",BasicDBObject.class);
		for(Iterator<BasicDBObject> iterator = result.iterator();iterator.hasNext();){
			DBObject obj = iterator.next();
			printInfo = JSON.toJSONString(obj);
		}
		if (printInfo==null){
			return "本月没有打印记录！";
		}else{
			return printInfo;
		}
		
	}

	/**
	 * 统计截止当前月的各月份的打印记录
	 */
	@Override
	public Map<String, Object> totalPrintWithPerMonth() {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//获取当前系统的月份
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH)+1;
		for(int i=1;i<=month;i++){
			String regex = i<10?"20180"+i:"2018"+i;
			Aggregation agg = Aggregation.newAggregation(
					Aggregation.match(Criteria.where("id").regex(regex)),
					Aggregation.group().sum("succeed_page").as("success_times").sum("failure_page").as("failure_tiems")
			);
			DBObject obj = getResult(agg);
			
			String result = JSON.toJSONString(obj);
			//String resultInfo = !result.equals("null")?result:"本月没有打印记录！";
			//resultMap.put(i+"月", resultInfo);
			if(result.equals("null")){
				resultMap.put(i+"月", 0);
			}else{
				int total = Integer.parseInt(obj.get("success_times").toString()) + Integer.parseInt(obj.get("failure_tiems").toString());
				resultMap.put(i+"月", total);
			}
		}
		
		return resultMap;
	}

}
