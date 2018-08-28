package com.dascom.operation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dascom.operation.entity.OpenidStatistics;
import com.dascom.operation.service.OpenidStatisticsService;

@RestController
public class OpenidController {
	
	@Autowired
	private OpenidStatisticsService openidService;
	
	@RequestMapping("getOpenid")
	public List<OpenidStatistics> getAll(){
		return openidService.getAll();
	}

}
