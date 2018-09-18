package com.dascom.operation.entity;

import java.io.Serializable;

public class ResultActive implements Serializable{

	private static final long serialVersionUID = -7227891872947598709L;
	
	private String activeId;
	private String activeTime;
	public String getActiveId() {
		return activeId;
	}
	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}
	public String getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}
	public ResultActive(String activeId, String activeTime) {
		super();
		this.activeId = activeId;
		this.activeTime = activeTime;
	}
	public ResultActive() {
		super();
	}
	
	
	
	

}
