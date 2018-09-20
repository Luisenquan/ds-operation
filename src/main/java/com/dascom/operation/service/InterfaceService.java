package com.dascom.operation.service;

import java.util.List;

import com.dascom.operation.entity.CollectionInterface;

public interface InterfaceService {
	
	List<CollectionInterface> getAllInterface();
	
	//添加测试接口到mongo
	void addInterface(CollectionInterface inter);
	
	
}
