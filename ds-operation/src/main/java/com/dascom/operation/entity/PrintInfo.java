package com.dascom.operation.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Field;

public class PrintInfo implements Serializable{

	private static final long serialVersionUID = -8548949244029285132L;
	
	@Field("print_date")
	private Date printDate;
	@Field("print_succed")
	private int printSucced;
	@Field("print_fail")
	private int printFail;
	public Date getPrintDate() {
		return printDate;
	}
	public void setPrintDate(Date printDate) {
		this.printDate = printDate;
	}
	public int getPrintSucced() {
		return printSucced;
	}
	public void setPrintSucced(int printSucced) {
		this.printSucced = printSucced;
	}
	public int getPrintFail() {
		return printFail;
	}
	public void setPrintFail(int printFail) {
		this.printFail = printFail;
	}
	public PrintInfo(Date printDate, int printSucced, int printFail) {
		super();
		this.printDate = printDate;
		this.printSucced = printSucced;
		this.printFail = printFail;
	}
	public PrintInfo() {
		super();
	}
	
	

}
