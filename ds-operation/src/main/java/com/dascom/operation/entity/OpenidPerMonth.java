package com.dascom.operation.entity;

import java.io.Serializable;

public class OpenidPerMonth implements Serializable{
	
	private String month;     //月份
	private int usedOpenid;   //当月使用的openid数
	private int addOpenid;    //当月新增的openid数
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getUsedOpenid() {
		return usedOpenid;
	}
	public void setUsedOpenid(int usedOpenid) {
		this.usedOpenid = usedOpenid;
	}
	public int getAddOpenid() {
		return addOpenid;
	}
	public void setAddOpenid(int addOpenid) {
		this.addOpenid = addOpenid;
	}
	public OpenidPerMonth(String month, int usedOpenid, int addOpenid) {
		super();
		this.month = month;
		this.usedOpenid = usedOpenid;
		this.addOpenid = addOpenid;
	}
	public OpenidPerMonth() {
		super();
	}
	
	

}
