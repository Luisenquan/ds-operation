package com.dascom.operation.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisService {
	
	//获取所有key
	Set<String> getAllkey();
	
	//获取所有hash中的key和对应的map
	Map<String,Map<Object,Object>> getAllHash();
	
	//从mongodb复制数据到redis 使用pipeline
	void copyData(Object obj) throws Exception;
	
	
	//Use redis pipeline
	Map<String, Map<String, String>> hgetByPipeline();
	
	//查询所有打印机状态
	Map<String, String> getPrinterState();
	
	//查询打印机设备状态是否占用
	boolean getAndSet(String number);
	
	//删除redis中该设备的占用标记
	void delUsing(String number);
	
}
