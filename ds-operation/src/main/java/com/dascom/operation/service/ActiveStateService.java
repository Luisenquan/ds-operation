package com.dascom.operation.service;

import java.util.Date;
import java.util.List;

import com.dascom.operation.entity.ActiveState;

public interface ActiveStateService {
	
	
	
	//获取所有
	List<ActiveState> getAll();
	
	//获取当前日的设备的在线时长
	List<ActiveState> getByNowDay();
	
	//按时间查询设备在线时长
	List<ActiveState> fecthByDate(Date date);
	
	//查询当前时间活跃的设备
	List<ActiveState> getActiveDevice(double time);

}
