package com.dascom.operation.service;

import java.util.List;

import com.dascom.operation.entity.OpenidStatistics;

public interface OpenidStatisticsService {
	
	//查询是否存在openid
	boolean checkOpenid(String openid);
	
	//获取当前日期的openid信息
	List<OpenidStatistics> getAll();
	
	//更新信息
	void update();
	
	//添加
	void add();

}
