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
	private String statistics_date;
	private int statistics_success;
	private int statistics_fail;
	private int print_total;
	private int total_openid;
	private int today_openid;
	private int new_openid;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatistics_date() {
		return statistics_date;
	}
	public void setStatistics_date(String statistics_date) {
		this.statistics_date = statistics_date;
	}
	public int getStatistics_success() {
		return statistics_success;
	}
	public void setStatistics_success(int statistics_success) {
		this.statistics_success = statistics_success;
	}
	public int getStatistics_fail() {
		return statistics_fail;
	}
	public void setStatistics_fail(int statistics_fail) {
		this.statistics_fail = statistics_fail;
	}
	public int getPrint_total() {
		return print_total;
	}
	public void setPrint_total(int print_total) {
		this.print_total = print_total;
	}
	public int getTotal_openid() {
		return total_openid;
	}
	public void setTotal_openid(int total_openid) {
		this.total_openid = total_openid;
	}
	public int getToday_openid() {
		return today_openid;
	}
	public void setToday_openid(int today_openid) {
		this.today_openid = today_openid;
	}
	public int getNew_openid() {
		return new_openid;
	}
	public void setNew_openid(int new_openid) {
		this.new_openid = new_openid;
	}
	public OpenidStatistics(String statistics_date, int statistics_success, int statistics_fail, int print_total,
			int total_openid, int today_openid, int new_openid) {
		super();
		this.statistics_date = statistics_date;
		this.statistics_success = statistics_success;
		this.statistics_fail = statistics_fail;
		this.print_total = print_total;
		this.total_openid = total_openid;
		this.today_openid = today_openid;
		this.new_openid = new_openid;
	}
	public OpenidStatistics() {
		super();
	}
	
	


}
