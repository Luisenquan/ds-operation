package com.dascom.operation.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Field;

public class PrintInfo implements Serializable{

	private static final long serialVersionUID = -8548949244029285132L;
	
	private String print_date;
	private int print_succed;
	private int print_fail;
	public String getPrint_date() {
		return print_date;
	}
	public void setPrint_date(String print_date) {
		this.print_date = print_date;
	}
	public int getPrint_succed() {
		return print_succed;
	}
	public void setPrint_succed(int print_succed) {
		this.print_succed = print_succed;
	}
	public int getPrint_fail() {
		return print_fail;
	}
	public void setPrint_fail(int print_fail) {
		this.print_fail = print_fail;
	}
	public PrintInfo(String print_date, int print_succed, int print_fail) {
		super();
		this.print_date = print_date;
		this.print_succed = print_succed;
		this.print_fail = print_fail;
	}
	public PrintInfo() {
		super();
	}
	
	
}
