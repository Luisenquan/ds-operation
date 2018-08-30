package com.dascom.operation.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="collection_openid_statistics")
public class OpenidStatistics implements Serializable{

	private static final long serialVersionUID = 8144994616314554094L;
	
	@Id
	private String id;
	private String openid;
	@Field("print_info")
	private List<PrintInfo> printInfo;
	@Field("first_using")
	private String firstUsing;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public List<PrintInfo> getPrintInfo() {
		return printInfo;
	}
	public void setPrintInfo(List<PrintInfo> printInfo) {
		this.printInfo = printInfo;
	}
	public String getFirstUsing() {
		return firstUsing;
	}
	public void setFirstUsing(String firstUsing) {
		this.firstUsing = firstUsing;
	}
	public OpenidStatistics(String id, String openid, List<PrintInfo> printInfo, String firstUsing) {
		super();
		this.id = id;
		this.openid = openid;
		this.printInfo = printInfo;
		this.firstUsing = firstUsing;
	}
	public OpenidStatistics() {
		super();
	}


}
