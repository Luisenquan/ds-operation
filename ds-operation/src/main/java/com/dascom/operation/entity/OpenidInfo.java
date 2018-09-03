package com.dascom.operation.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="collection_openid_info")
public class OpenidInfo implements Serializable{

	private static final long serialVersionUID = 8144994616314554094L;
	
	@Id
	private String id;
	private String openid;
	private List<PrintInfo> print_info;
	private String first_using;
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
	public List<PrintInfo> getPrint_info() {
		return print_info;
	}
	public void setPrint_info(List<PrintInfo> print_info) {
		this.print_info = print_info;
	}
	public String getFirst_using() {
		return first_using;
	}
	public void setFirst_using(String first_using) {
		this.first_using = first_using;
	}
	public OpenidInfo(String id, String openid, List<PrintInfo> print_info, String first_using) {
		super();
		this.id = id;
		this.openid = openid;
		this.print_info = print_info;
		this.first_using = first_using;
	}
	public OpenidInfo() {
		super();
	}
	
	


}
