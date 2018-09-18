package com.lui.operation.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.lui.operation.entity.CollectionImages;
import com.lui.operation.service.ImagesService;

@RestController
public class ImagesController {
	
	@Autowired
	private ImagesService imagesService;
	
	@RequestMapping("fetch")
	public List<CollectionImages> findAll(){
		return imagesService.findAllImages();
	}
	
	@RequestMapping(value="insertImage",method=RequestMethod.POST)
	public void insert(@RequestBody String images) {
		JSONObject obj = JSONObject.parseObject(images);
		String img = obj.get("base64").toString();
		imagesService.insertImages(img);
	}
	
	@RequestMapping("/delete/{id}")
	public void del(@PathVariable(value="id")String id) {
		imagesService.delImages(id);
		
	}

}
