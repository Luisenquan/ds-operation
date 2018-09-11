package com.dascom.operation.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 设备在线状态
 * @author Leisenquan
 * @time 2018年8月27日 下午4:55:55
 * @project_name ds-operation
 */

@Document(collection="collection_active_state")
public class ActiveState implements Serializable{

	private static final long serialVersionUID = -7421834075567757233L;
	
	@Id
	private String _id;
	@Field("active_id")
	private String activeId;
	@Field("latest_statistical")
	private Date latestStatistical;
	@Field("online_time")
	private long onlineTime;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getActiveId() {
		return activeId;
	}
	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}
	public Date getLatestStatistical() {
		return latestStatistical;
	}
	public void setLatestStatistical(Date latestStatistical) {
		this.latestStatistical = latestStatistical;
	}
	public long getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(long onlineTime) {
		this.onlineTime = onlineTime;
	}
	public ActiveState(String activeId, Date latestStatistical, long onlineTime) {
		super();
		this.activeId = activeId;
		this.latestStatistical = latestStatistical;
		this.onlineTime = onlineTime;
	}
	public ActiveState() {
		super();
	}
	
	
	
}
