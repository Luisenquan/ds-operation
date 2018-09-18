package com.dascom.operation.service;

import java.util.List;
import java.util.Map;

import com.dascom.operation.entity.CollectionPrinterActiveStatistics;


public interface CollectionPrintersService {
	
	//获取设备总数
	int getAllDevice();
	
	
	
	//统计每日设备上线数
	Map<String,Object> getOnlineDevice(int skip);
	
	//统计每月设备登陆数
	Map<String,Integer> getOnlinePerMonth();

}
