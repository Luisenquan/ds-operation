package com.lui.operation.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="collection_active_state")
public class Active implements Serializable{

	private static final long serialVersionUID = 8285513903099382576L;
	
	@Id
	private String _id;
	private String active_id;
	private Date latest_statistical;
	private long online_time;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getActive_id() {
		return active_id;
	}
	public void setActive_id(String active_id) {
		this.active_id = active_id;
	}
	public Date getLatest_statistical() {
		return latest_statistical;
	}
	public void setLatest_statistical(Date latest_statistical) {
		this.latest_statistical = latest_statistical;
	}
	public long getOnline_time() {
		return online_time;
	}
	public void setOnline_time(long online_time) {
		this.online_time = online_time;
	}
	

}
