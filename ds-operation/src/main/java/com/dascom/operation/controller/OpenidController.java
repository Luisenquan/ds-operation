package com.dascom.operation.controller;


import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.dascom.operation.entity.OpenidInfo;
import com.dascom.operation.service.OpenidInfoService;


@RestController
public class OpenidController {
	
	@Autowired
	private OpenidInfoService openidInfoService;
	
	
	
	@RequestMapping("getOpenid")
	public List<OpenidInfo> getAll(){
		return openidInfoService.getAll();
	}
	
	@RequestMapping("getOpenidNowDay")
	public List<OpenidInfo> getNowDay(){
		return openidInfoService.fetchByNowDay();
	}
	
	
	
	@RequestMapping(value="saveOpenid",method=RequestMethod.POST)
	public void saveOpenId(HttpServletRequest req,HttpServletResponse resp,@RequestBody JSONObject obj) {
		String openid = (String)obj.get("openid");
		int printSucced = (int) obj.get("success");
		int printFail = (int) obj.get("fail");
		boolean check = openidInfoService.checkOpenid(openid);
		if(check) {
			//更新
			openidInfoService.update(openid, printSucced, printFail);
		}else {
			//插入
			openidInfoService.add(openid, printSucced, printFail);
		}
	}
	
	
	@RequestMapping("newOpenidByDay")
	public void newOpenidByDay() {
		openidInfoService.newOpenidByDay();
	}
	
}
