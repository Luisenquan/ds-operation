package com.dascom.operation.entity.tcp;

import java.io.Serializable;

/**
 * 控制通道实体类
 * @author Leisenquan
 * @time 2018年9月21日 下午3:55:35
 * @project_name ds-operation
 */
public class ControlEntity implements Serializable{

	private static final long serialVersionUID = 6277493755717456102L;
	private String id;  //uuid
	private String number; //设备编号
	private String instruct;  //请求指令
	private String controlType; //控制类型,默认00(00表示wifi,01表示设备)
	private String data; //base64数据
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getInstruct() {
		return instruct;
	}

	public void setInstruct(String instruct) {
		this.instruct = instruct;
	}

	public String getControlType() {
		return controlType;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	

	public ControlEntity() {
		super();
	}

	public ControlEntity(String id, String number, String instruct, String controlType, String data) {
		super();
		this.id = id;
		this.number = number;
		this.instruct = instruct;
		this.controlType = controlType;
		this.data = data;
	}
	
	

}
