package com.dascom.operation.service;

import java.util.List;

import com.dascom.operation.entity.OpenidStatistics;

public interface OpenidStatisticsService {
	
	
	//插入当天统计数据
	void insertStatistics();
	
	//返回查询列表
	List<OpenidStatistics> getOpenidStatisticsList();

}
