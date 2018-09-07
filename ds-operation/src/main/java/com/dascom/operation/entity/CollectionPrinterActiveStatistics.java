package com.dascom.operation.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="collection_printer_active_statistics")
public class CollectionPrinterActiveStatistics implements Serializable{
	
	@Id
	private String _id;
	private String id;
	private int count;
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public CollectionPrinterActiveStatistics(String _id, String id, int count) {
		super();
		this._id = _id;
		this.id = id;
		this.count = count;
	}
	public CollectionPrinterActiveStatistics() {
		super();
	}
	
	
	
	

}
