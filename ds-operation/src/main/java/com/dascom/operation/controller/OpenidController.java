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
import com.dascom.operation.entity.OpenidStatistics;
import com.dascom.operation.service.OpenidStatisticsService;

@RestController
public class OpenidController {
	
	@Autowired
	private OpenidStatisticsService openidService;
	
	@RequestMapping("getOpenid")
	public List<OpenidStatistics> getAll(){
		return openidService.getAll();
	}
	
	@RequestMapping("getOpenidNowDay")
	public List<OpenidStatistics> getNowDay(){
		return openidService.fetchByNowDay();
	}
	
	
	
	@RequestMapping(value="saveOpenid",method=RequestMethod.POST)
	public void saveOpenId(HttpServletRequest req,HttpServletResponse resp,@RequestBody JSONObject obj) {
		String openid = (String)obj.get("openid");
		int printSucced = (int) obj.get("success");
		int printFail = (int) obj.get("fail");
		boolean check = openidService.checkOpenid(openid);
		if(check) {
			//更新
			openidService.update(openid, printSucced, printFail);
		}else {
			//插入
			openidService.add(openid, printSucced, printFail);
		}
	}

}
