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
import com.dascom.operation.entity.OpenidStatistics;
import com.dascom.operation.entity.PrintInfo;
import com.dascom.operation.service.OpenidStatisticsService;
import com.dascom.operation.utils.FormatDate;

@Component("openidService")
public class OpenidStatisticsServiceImpl implements OpenidStatisticsService {

	@Autowired
	@Qualifier("operationMongoTemplate")
	MongoTemplate operationMongoTemplate;

	@Override
	public List<OpenidStatistics> getAll() {
		return operationMongoTemplate.findAll(OpenidStatistics.class);
	}

	@Override
	public void update(String openid, int succed, int fail) {
		String printDate = FormatDate.getTheDate();
		Query query = new Query();
		query.addCriteria(Criteria.where("openid").is(openid));
		OpenidStatistics openidStatistics = (OpenidStatistics) operationMongoTemplate.findOne(query,
				OpenidStatistics.class);
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
		 OpenidStatistics.class);
		 
	}


	@Override
	public void add(String openid, int succed, int fail) {
		String date = FormatDate.getTheDate();
		List<PrintInfo> list = new ArrayList<PrintInfo>();
		PrintInfo printInfo = new PrintInfo(date, succed, fail);
		list.add(printInfo);
		OpenidStatistics openidStatistics = new OpenidStatistics(null, openid, list, date);
		operationMongoTemplate.insert(openidStatistics);

	}
	
	@Override
	public boolean checkOpenid(String openid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("openid").is(openid));
		OpenidStatistics result = operationMongoTemplate.findOne(query, OpenidStatistics.class);
		if (!StringUtils.isEmpty(result)) {
			return true; // 存在openID 更新
		}
		return false; // 不存在 插入
	}

	@Override
	public List<OpenidStatistics> fetchByNowDay() {
		String nowDay = FormatDate.getTheDate();
		Query query = new Query();
		query.addCriteria(Criteria.where("firstUsing").is(nowDay));
		return operationMongoTemplate.find(query, OpenidStatistics.class);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
