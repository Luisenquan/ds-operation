package com.dascom.operation.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import com.dascom.operation.service.OpenidInfoService;
import com.dascom.operation.service.OpenidStatisticsService;

public class OpenidStatisticsServiceImpl implements OpenidStatisticsService{
	
	@Autowired
	private OpenidInfoService openidInfoService;

	@Override
	public void insert() {
		
	}

}
