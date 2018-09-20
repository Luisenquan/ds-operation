package com.dascom.operation.service;

import java.util.List;
import java.util.Map;

import com.dascom.operation.entity.PrintStatistics;

public interface PrintStatisticeService {
	
	//获取所有
	List<PrintStatistics> getAll();
	
	//按月份查询
	List<PrintStatistics> getMonth(String date);
	
	//获取每台设备的总打印数
	List<String> getPrintTotal();
	
	//计算每台设备的成功打印次数
	String totalSucceedTimes(String number);
	
	//统计所有设备的总打印数
	int totalPrintTimes();
	
	//按月统计设备的打印情况
	String totalPrintWithMonth(String date);
	
	//统计截止当前月的各月份的打印记录
	Map<String,Integer> totalPrintWithPerMonth();

}
