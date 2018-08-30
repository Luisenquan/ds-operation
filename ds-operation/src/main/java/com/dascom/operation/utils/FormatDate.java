package com.dascom.operation.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 格式化时间
 * 
 * @author Leisenquan
 * @time 2018年8月28日 上午11:44:47
 * @project_name ds-operation
 */
public class FormatDate {

	/**
	 * 毫秒数格式化
	 * @param time
	 * @return
	 */
	public static String formatDuring(long time) {
		long days = time / (1000 * 60 * 60 * 24);
		long hours = (time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (time % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (time % (1000 * 60)) / 1000;
		return days + " days " + hours + " hours " + minutes + " minutes " + seconds + " seconds ";
	}
	
	public static String getTheDate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}
	
}
