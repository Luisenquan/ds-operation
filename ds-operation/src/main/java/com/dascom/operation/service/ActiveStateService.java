package com.dascom.operation.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dascom.operation.entity.ActiveState;

public interface ActiveStateService {

	//获取所有
	List<ActiveState> getAll();
	
	//获取当前日的设备的在线时长
	Map<String,Object> getByNowDay(int skip);
	
	//按时间查询设备在线时长
	List<ActiveState> fecthByDate(Date date);
	
	//查询当前时间活跃的设备
	Map<String,Object> getActiveDevice(double time,int pageNum);

}
