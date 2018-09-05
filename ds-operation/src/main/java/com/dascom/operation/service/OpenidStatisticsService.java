package com.dascom.operation.service;

import java.util.List;
import java.util.Map;

import com.dascom.operation.entity.OpenidPerMonth;
import com.dascom.operation.entity.OpenidStatistics;

public interface OpenidStatisticsService {
	
	
	//插入当天统计数据
	void insertStatistics();
	
	//返回查询列表
	List<OpenidStatistics> getOpenidStatisticsList();
	
	//统计每月openid新增数
	Map<String,Object> monthlyStatistics();

}
