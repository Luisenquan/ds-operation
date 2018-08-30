package com.dascom.operation.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dascom.operation.entity.OpenidInfo;
import com.dascom.operation.entity.PrintInfo;
import com.dascom.operation.service.OpenidInfoService;
import com.dascom.operation.utils.FormatDate;

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
		List<PrintInfo> list = openidStatistics.getPrintInfo();
		PrintInfo info = list.get(list.size() - 1);
		 if(printDate.equals(info.getPrintDate())) {
			//更新成功和失败次数
			 info.setPrintSucced(info.getPrintSucced()+succed);
			 info.setPrintFail(info.getPrintFail()+fail);
			 list.set(list.size()-1, info);
		 }else {
			 //添加一条记录
			 PrintInfo addInfo = new PrintInfo(printDate, succed, fail);
			 list.add(addInfo);
		 }
		 Update update = new Update(); update.set("printInfo",list); 
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
			return true; // 存在openID 更新
		}
		return false; // 不存在 插入
	}

	@Override
	public List<OpenidInfo> fetchByNowDay() {
		String nowDay = FormatDate.getTheDate();
		Query query = new Query();
		query.addCriteria(Criteria.where("firstUsing").is(nowDay));
		return operationMongoTemplate.find(query, OpenidInfo.class);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
