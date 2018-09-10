package com.dascom.operation.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.dascom.operation.entity.ActiveState;
import com.dascom.operation.service.ActiveStateService;
import com.dascom.operation.utils.HttpClientUtils;


@Component("activeStateService")
public class ActiveStateServiceImpl implements ActiveStateService{
	
	private static final Logger logger = LogManager.getLogger(ActiveStateServiceImpl.class);
	
	@Value("${statistics_online}")
	private String statisticsOnline;
	
	@Autowired
	@Qualifier("cloudDeviceMongoTemplate")
	MongoTemplate cloudDeviceMongoTemplate;

	@Override
	public List<ActiveState> getAll() {
		return cloudDeviceMongoTemplate.findAll(ActiveState.class);
	}

	@Override
	public List<ActiveState> getByNowDay() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String nowDate = month<10?year+"0"+month:year+month+"";
		nowDate = day<10?nowDate+"0"+day:nowDate+day;
		Query query = new Query();
		query.addCriteria(Criteria.where("activeId").regex(nowDate));
		return cloudDeviceMongoTemplate.find(query, ActiveState.class);
	}

	@Override
	public List<ActiveState> fecthByDate(Date date) {
		Query query = new Query();
		query.addCriteria(Criteria.where("activeId").regex("date"));
		return cloudDeviceMongoTemplate.find(query, ActiveState.class);
	}

	//请求统计设备时长接口
	@Override
	public void getOnline() {
		String uuid= UUID.randomUUID().toString();
		String jsonPara = "{\"id\":\""+uuid+"\"}";
		logger.info(jsonPara);
		String headerPara = "{\"Content-Type\":\"application/json\"}";
		logger.info(headerPara);
		Map<String,Object> resultMap = HttpClientUtils.doPost(statisticsOnline, jsonPara, headerPara);
		int statusCode = (int) resultMap.get("statusCode");
		String resultLine = (String)resultMap.get("resultLine");
		if(statusCode<400) {
			logger.info("-------请求成功！查询数据库------");
		}else {
			logger.error("------请求失败！错误码："+statusCode+"------");
			logger.error("------失败原因------"+resultLine);
		}
	}

	@Override
	public List<ActiveState> getActiveDevice(double time) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String nowDate = month<10?year+"0"+month:year+month+"";
		nowDate = day<10?nowDate+"0"+day:nowDate+day;
		//将时间转为毫秒
		long ms = (long) (time*3600000);
		Query query = new Query();
		query.addCriteria(Criteria.where("activeId").regex(nowDate));
		query.addCriteria(Criteria.where("onlineTime").gt(ms));
		
		return cloudDeviceMongoTemplate.find(query, ActiveState.class);
	}

}
