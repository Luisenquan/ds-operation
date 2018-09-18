package com.lui.operation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lui.operation.entity.Active;
import com.lui.operation.service.ActiveService;

@RestController
public class ActiveController {
	
	@Autowired
	private ActiveService activeService;
	
	@RequestMapping("fetchActive")
	public List<Active> fetchActive(){
		return activeService.fetchActive();
	}

}
