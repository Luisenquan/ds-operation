package com.dascom.operation.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dascom.operation.entity.OperationOpenidStatistics;
import com.dascom.operation.service.OpenidStatisticsService;

@RestController
public class OpenidController {
	
	@Autowired
	private OpenidStatisticsService openidStatisticsService;
	
	@RequestMapping("findOpenidByDay")
	public List<OperationOpenidStatistics> findByDay(){
		return openidStatisticsService.getOpenidStatistics();
	}
	
	@RequestMapping("findOpenidByMonth")
	public Map<Object,Object> findByMonth(){
		return openidStatisticsService.getOpenidStatisticsPerMonth();
		
	}

}
