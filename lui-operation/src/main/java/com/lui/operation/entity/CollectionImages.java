package com.lui.operation.entity;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="collection_images")
public class CollectionImages implements Serializable{
	
	@Id
	private String _id;
	private String images;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	public CollectionImages(String images) {
		super();
		this.images = images;
	}
	public CollectionImages() {
		super();
	}
	
}
