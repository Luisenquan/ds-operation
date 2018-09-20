package com.dascom.operation.service;

import java.util.List;
import java.util.Map;

import com.dascom.operation.entity.OperationOpenidStatistics;

public interface OpenidStatisticsService {
	
	//获取每日openid统计情况
	Map<String,Object> getOpenidStatistics(int pageNum);
	
	//按月统计openid使用情况
	Map<Object,Object> getOpenidStatisticsPerMonth();

}
