package com.dascom.operation.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.dascom.operation.entity.ResultActive;
import com.dascom.operation.service.ActiveStateService;
import com.dascom.operation.utils.FormatDate;


@Component("activeStateService")
public class ActiveStateServiceImpl implements ActiveStateService{
	
	private static final Logger logger = LogManager.getLogger(ActiveStateServiceImpl.class);
	
	@Value("${statistics_online}")
	private String statisticsOnline;
	
	@Autowired
	@Qualifier("cloudDeviceMongoTemplate")
	MongoTemplate cloudDeviceMongoTemplate;
	
	private List<ResultActive> getDataList(List<ActiveState> list,int year){
		String y = String.valueOf(year);
		List<ResultActive> dataList = new ArrayList<ResultActive>();
		for(ActiveState active : list) {
			String activeId = active.getActiveId();
			activeId = activeId.substring(0, activeId.indexOf(y));
			long onlineTime = active.getOnlineTime();
			String runTime = FormatDate.formatDuring(onlineTime);
			ResultActive resultActive = new ResultActive(activeId, runTime);
			dataList.add(resultActive);
		}
		return dataList;
	}

	@Override
	public List<ActiveState> getAll() {
		return cloudDeviceMongoTemplate.findAll(ActiveState.class);
	}

	@Override
	public Map<String,Object> getByNowDay(int pageNum) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		int pageSize = 10;
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DAY_OF_MONTH)-1;
		String nowDate = month<10?year+"0"+month:year+month+"";
		nowDate = day<10?nowDate+"0"+day:nowDate+day;
		Query query = new Query();
		query.addCriteria(Criteria.where("activeId").regex(nowDate));
		query.skip((pageNum-1)*pageSize).limit(pageSize);
		//计算总页数
		long sum = cloudDeviceMongoTemplate.count(query, ActiveState.class);
		int totalPage = (int)(sum%pageSize==0?sum/pageSize:sum/pageSize+1);
		List<ActiveState> dataList = cloudDeviceMongoTemplate.find(query, ActiveState.class);
		List<ResultActive> resultActive = getDataList(dataList, year);
		resultMap.put("totalPage", totalPage);
		resultMap.put("data", resultActive);
		return resultMap;
	}

	@Override
	public List<ActiveState> fecthByDate(Date date) {
		Query query = new Query();
		query.addCriteria(Criteria.where("activeId").regex("date"));
		return cloudDeviceMongoTemplate.find(query, ActiveState.class);
	}

	

	@Override
	public Map<String,Object> getActiveDevice(double time,int pageNum) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		int pageSize = 10;
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DAY_OF_MONTH)-1;
		String nowDate = month<10?year+"0"+month:year+month+"";
		nowDate = day<10?nowDate+"0"+day:nowDate+day;
		//将时间转为毫秒
		long ms = (long) (time*3600000);
		Query query = new Query();
		query.addCriteria(Criteria.where("activeId").regex(nowDate));
		query.addCriteria(Criteria.where("onlineTime").gt(ms));
		query.skip((pageNum-1)*pageSize).limit(pageSize);
		//计算总页数
		long sum = cloudDeviceMongoTemplate.count(query, ActiveState.class);
		int totalPage = (int)(sum%pageSize==0?sum/pageSize:sum/pageSize+1);
		List<ActiveState> dataList = cloudDeviceMongoTemplate.find(query, ActiveState.class);
		List<ResultActive> resultActive = getDataList(dataList, year);
		resultMap.put("totalPage", totalPage);
		resultMap.put("data", resultActive);
		return resultMap;
	}

}
