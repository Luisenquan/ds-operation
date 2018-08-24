package com.dascom.operation.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="collection_operation")
public class CollectionInterface implements Serializable{

	private static final long serialVersionUID = -956081880943583457L;
	
	@Id
	private String id;
	private String interfaceName;
	private String method;
	private String requestUrl;
	private String jsonParameter;
	private String headerParameter;
	private String note;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getRequestUrl() {
		return requestUrl;
	}
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	public String getJsonParameter() {
		return jsonParameter;
	}
	public void setJsonParameter(String jsonParameter) {
		this.jsonParameter = jsonParameter;
	}
	public String getHeaderParameter() {
		return headerParameter;
	}
	public void setHeaderParameter(String headerParameter) {
		this.headerParameter = headerParameter;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public CollectionInterface(String id, String interfaceName, String method, String requestUrl, String jsonParameter,
			String headerParameter, String note) {
		super();
		this.id = id;
		this.interfaceName = interfaceName;
		this.method = method;
		this.requestUrl = requestUrl;
		this.jsonParameter = jsonParameter;
		this.headerParameter = headerParameter;
		this.note = note;
	}
	public CollectionInterface() {
		super();
	}
	
	
	
	
	

}
