package com.dascom.operation.utils;

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

}
