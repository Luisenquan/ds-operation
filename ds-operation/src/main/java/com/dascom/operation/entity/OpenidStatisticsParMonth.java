package com.dascom.operation.entity;

import java.io.Serializable;

public class OpenidStatisticsParMonth implements Serializable{
	

	private static final long serialVersionUID = -7522104708755559440L;
	
	

	private int printTotalMonth;          //月打印总数
	private int totalOpenidMonth;    //月openid使用总数
	private int newOpendiMonth;      //月openid新增总数
	public int getPrintTotalMonth() {
		return printTotalMonth;
	}
	public void setPrintTotalMonth(int printTotalMonth) {
		this.printTotalMonth = printTotalMonth;
	}
	public int getTotalOpenidMonth() {
		return totalOpenidMonth;
	}
	public void setTotalOpenidMonth(int totalOpenidMonth) {
		this.totalOpenidMonth = totalOpenidMonth;
	}
	public int getNewOpendiMonth() {
		return newOpendiMonth;
	}
	public void setNewOpendiMonth(int newOpendiMonth) {
		this.newOpendiMonth = newOpendiMonth;
	}
	public OpenidStatisticsParMonth(int printTotalMonth, int totalOpenidMonth, int newOpendiMonth) {
		super();
		this.printTotalMonth = printTotalMonth;
		this.totalOpenidMonth = totalOpenidMonth;
		this.newOpendiMonth = newOpendiMonth;
	}
	public OpenidStatisticsParMonth() {
		super();
	}

	
	
	

}
