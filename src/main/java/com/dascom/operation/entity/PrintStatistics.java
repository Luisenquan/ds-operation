package com.dascom.operation.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="collection_print_statistics")
public class PrintStatistics {
	
	@Id
	private String _id;
	private String id;
	private String number;
	private Integer succeed_page;
	private Integer failure_page;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
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
	public Integer getSucceed_page() {
		return succeed_page;
	}
	public void setSucceed_page(Integer succeed_page) {
		this.succeed_page = succeed_page;
	}
	public Integer getFailure_page() {
		return failure_page;
	}
	public void setFailure_page(Integer failure_page) {
		this.failure_page = failure_page;
	}
	
	

}
