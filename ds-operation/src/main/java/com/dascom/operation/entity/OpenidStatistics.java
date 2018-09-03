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
	@Field("print_total")
	private int printTotal;
	public int getPrintTotal() {
		return printTotal;
	}

	public void setPrintTotal(int printTotal) {
		this.printTotal = printTotal;
	}

	@Field("total_openid")
	private int totalOpenid;
	@Field("new_openid")
	private int newOpenid;

	public int getNewOpenid() {
		return newOpenid;
	}

	public void setNewOpenid(int newOpenid) {
		this.newOpenid = newOpenid;
	}

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

	

	

	public OpenidStatistics(String statisticsDate, int statisticsSuccess, int statisticsFail, int printTotal,
			int totalOpenid, int newOpenid) {
		super();
		this.statisticsDate = statisticsDate;
		this.statisticsSuccess = statisticsSuccess;
		this.statisticsFail = statisticsFail;
		this.printTotal = printTotal;
		this.totalOpenid = totalOpenid;
		this.newOpenid = newOpenid;
	}

	public OpenidStatistics() {
		super();
	}

}
