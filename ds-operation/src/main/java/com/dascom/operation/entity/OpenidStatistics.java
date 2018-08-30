package com.dascom.operation.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "collection_openid_statistics")
public class OpenidStatistics implements Serializable {

	private static final long serialVersionUID = -1050265532084997688L;
	
	@Id
	private String id;
	@Field("statistics_date")
	private String statisticsDate;
	@Field("statistics_success")
	private int statisticsSuccess;
	@Field("statistics_fail")
	private int statisticsFail;
	@Field("total_openid")
	private int totalOpenid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatisticsDate() {
		return statisticsDate;
	}

	public void setStatisticsDate(String statisticsDate) {
		this.statisticsDate = statisticsDate;
	}

	public int getStatisticsSuccess() {
		return statisticsSuccess;
	}

	public void setStatisticsSuccess(int statisticsSuccess) {
		this.statisticsSuccess = statisticsSuccess;
	}

	public int getStatisticsFail() {
		return statisticsFail;
	}

	public void setStatisticsFail(int statisticsFail) {
		this.statisticsFail = statisticsFail;
	}

	public int getTotalOpenid() {
		return totalOpenid;
	}

	public void setTotalOpenid(int totalOpenid) {
		this.totalOpenid = totalOpenid;
	}

	public OpenidStatistics(String statisticsDate, int statisticsSuccess, int statisticsFail, int totalOpenid) {
		super();
		this.statisticsDate = statisticsDate;
		this.statisticsSuccess = statisticsSuccess;
		this.statisticsFail = statisticsFail;
		this.totalOpenid = totalOpenid;
	}

	public OpenidStatistics() {
		super();
	}

}
