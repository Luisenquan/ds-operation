package com.dascom.operation.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dascom.operation.entity.ActiveState;
import com.dascom.operation.service.ActiveStateService;
import com.dascom.operation.utils.FormatDate;

@RestController
public class ActiveStateController {
	
	@Autowired
	private ActiveStateService activeStateService;
	
	@RequestMapping("active")
	public List<ActiveState> getAll(){
		return activeStateService.getAll();
	}
	
	@RequestMapping("getService")
	public void getService() {
		activeStateService.getOnline();
	}
	
	@RequestMapping("getNowDay")
	public List<ActiveState> nowDay(){
		//activeStateService.getOnline();
		return activeStateService.getByNowDay();
	}
	
	
	//获取活跃设备（可按时长查询）
	@RequestMapping(value="getActiveDevice",method=RequestMethod.GET)
	public Map<String,String> getActive(@RequestParam(defaultValue="0") double time){
		//请求统计设备时长接口
		//activeStateService.getOnline();
		
		//查询设备在线时长
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		Map<String,String>resultMap = new HashMap<String,String>();
		List<ActiveState> actives = new ArrayList<ActiveState>();
		if(time==0) {
			actives = activeStateService.getByNowDay();
		}else {
			actives = activeStateService.getActiveDevice(time);
		}
		
		for(ActiveState active : actives) {
			String activeId = active.getActiveId();
			activeId = activeId.substring(0, activeId.indexOf(year));
			long onlineTime = active.getOnlineTime();
			String runTime = FormatDate.formatDuring(onlineTime);
			resultMap.put(activeId, String.valueOf(onlineTime));
		}
		
		return resultMap;
	}

}
