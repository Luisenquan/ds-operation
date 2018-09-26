package com.dascom.operation.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="operation_openid_statistics")
public class OperationOpenidStatistics implements Serializable{
	

	private static final long serialVersionUID = -6951301873293971005L;
	
	/*@Id
	private String _id;*/
	private String statistics_date;       //统计时间
	private int print_total;             //打印总数
	private int total_openid;           //openid总数  
	private int today_openid;          //当天openid使用数量
	private int new_openid;           //当天openid新增数量
	/*public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}*/
	public String getStatistics_date() {
		return statistics_date;
	}
	public void setStatistics_date(String statistics_date) {
		this.statistics_date = statistics_date;
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
	public OperationOpenidStatistics(String statistics_date, int print_total, int total_openid, int today_openid,
			int new_openid) {
		super();
		this.statistics_date = statistics_date;
		this.print_total = print_total;
		this.total_openid = total_openid;
		this.today_openid = today_openid;
		this.new_openid = new_openid;
	}
	public OperationOpenidStatistics() {
		super();
	}
	
	

}
