package com.dascom.operation.service.impl;

import java.util.ArrayList;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.dascom.operation.entity.OpenidInfo;
import com.dascom.operation.entity.PrintInfo;
import com.dascom.operation.service.OpenidInfoService;
import com.dascom.operation.utils.AggreationWithResult;
import com.dascom.operation.utils.FormatDate;
import com.mongodb.DBObject;

@Component("openidInfoService")
public class OpenidInfoServiceImpl implements OpenidInfoService {

	@Autowired
	@Qualifier("operationMongoTemplate")
	MongoTemplate operationMongoTemplate;

	@Override
	public List<OpenidInfo> getAll() {
		return operationMongoTemplate.findAll(OpenidInfo.class);
	}

	@Override
	public void update(String openid, int succed, int fail) {
		String printDate = FormatDate.getTheDate();
		Query query = new Query();
		query.addCriteria(Criteria.where("openid").is(openid));
		OpenidInfo openidStatistics = (OpenidInfo) operationMongoTemplate.findOne(query,
				OpenidInfo.class);
		List<PrintInfo> list = openidStatistics.getPrint_info();
		PrintInfo info = list.get(list.size() - 1);
		 if(printDate.equals(info.getPrint_date())) {
			//更新成功和失败次数
			 info.setPrint_succed(info.getPrint_succed()+succed);
			 info.setPrint_fail(info.getPrint_fail()+fail);
			 list.set(list.size()-1, info);
		 }else {
			 //添加一条记录
			 PrintInfo addInfo = new PrintInfo(printDate, succed, fail);
			 list.add(addInfo);
		 }
		 Update update = new Update(); update.set("print_info",list); 
		 operationMongoTemplate.updateFirst(query, update,
				 OpenidInfo.class);
		 
	}


	@Override
	public void add(String openid, int succed, int fail) {
		String date = FormatDate.getTheDate();
		List<PrintInfo> list = new ArrayList<PrintInfo>();
		PrintInfo printInfo = new PrintInfo(date, succed, fail);
		list.add(printInfo);
		OpenidInfo openidStatistics = new OpenidInfo(null, openid, list, date);
		operationMongoTemplate.insert(openidStatistics);

	}
	
	@Override
	public boolean checkOpenid(String openid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("openid").is(openid));
		OpenidInfo result = operationMongoTemplate.findOne(query, OpenidInfo.class);
		if (!StringUtils.isEmpty(result)) {
			return true; // 存在  更新
		}
		return false; // 不存在 插入
	}

	@Override
	public List<OpenidInfo> fetchByNowDay() {
		String nowDay = FormatDate.getTheDate();
		Query query = new Query();
		query.addCriteria(Criteria.where("first_using").is("20180830"));
		return operationMongoTemplate.find(query, OpenidInfo.class);
	}

	@Override
	public void newOpenidByDay() {
		Aggregation agg = Aggregation.newAggregation(
				Aggregation.match(Criteria.where("first_using").is("20180829")),
				Aggregation.group().count().as("当天总人数")
				);
		//"collection_openid_info"
		AggreationWithResult result = new AggreationWithResult();
		DBObject obj = result.getResult(agg, operationMongoTemplate, "collection_openid_info");
		System.out.println(JSON.toJSON(obj));
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
