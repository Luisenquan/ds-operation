package com.lui.operation.service.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.lui.operation.entity.CollectionImages;
import com.lui.operation.service.ImagesService;


@Component("imagesService")
public class ImagesServiceImpl implements ImagesService{
	
	@Autowired
	private MongoTemplate template;

	@Override
	public List<CollectionImages> findAllImages() {
		return template.findAll(CollectionImages.class);
	}

	@Override
	public void insertImages(String images) {
		CollectionImages iamge = new CollectionImages(images);
		template.insert(iamge);
	}

	@Override
	public void delImages(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
		template.remove(query,CollectionImages.class);
	}

}
