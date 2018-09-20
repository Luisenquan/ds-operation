package com.dascom.operation.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="collection_openid_statistics")
public class OpenidStatistics implements Serializable{
	
	private static final long serialVersionUID = -172393786725273179L;
	
	@Id
	private String _id; 
	private String openid;
	private Date createDate;
	private int printNumber;
	private Date lastModifyDate;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getPrintNumber() {
		return printNumber;
	}
	public void setPrintNumber(int printNumber) {
		this.printNumber = printNumber;
	}
	public Date getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	public OpenidStatistics(String openid, Date createDate, int printNumber, Date lastModifyDate) {
		super();
		this.openid = openid;
		this.createDate = createDate;
		this.printNumber = printNumber;
		this.lastModifyDate = lastModifyDate;
	}
	public OpenidStatistics() {
		super();
	}
	
	

}
