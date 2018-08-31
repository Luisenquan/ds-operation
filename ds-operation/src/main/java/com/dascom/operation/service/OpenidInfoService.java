package com.dascom.operation.service;

import java.util.List;

import com.dascom.operation.entity.OpenidInfo;


public interface OpenidInfoService {

	//获取所有openid
	List<OpenidInfo> getAll();
	
	//查询是否存在openid
	boolean checkOpenid(String openid);
	
	//更新信息
	void update(String openid,int succed,int fail);
	
	//添加
	void add(String openid,int succed,int fail);
	
	//查询当前日期的所有openId
	List<OpenidInfo> fetchByNowDay();
	
	

}
