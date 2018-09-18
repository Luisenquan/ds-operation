package com.lui.operation.service;

import java.util.List;

import com.lui.operation.entity.CollectionImages;

public interface ImagesService {
	
	//查询所有
	List<CollectionImages> findAllImages();
	//插入
	void insertImages(String images);
	//删除
	void delImages(String id);

}
