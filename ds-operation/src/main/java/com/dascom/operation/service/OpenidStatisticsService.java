package com.dascom.operation.service;

import java.util.List;

import com.dascom.operation.entity.OpenidStatistics;
import com.dascom.operation.entity.PrintInfo;

public interface OpenidStatisticsService {

	//获取所有openid
	List<OpenidStatistics> getAll();
	
	//查询是否存在openid
	boolean checkOpenid(String openid);
	
	//更新信息
	void update(String openid,int succed,int fail);
	
	//添加
	void add(String openid,int succed,int fail);
	
	//查询当前日期的所有openId
	List<OpenidStatistics> fetchByNowDay();

}
