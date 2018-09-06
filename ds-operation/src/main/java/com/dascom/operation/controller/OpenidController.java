package com.dascom.operation.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.dascom.operation.entity.OpenidStatistics;
import com.dascom.operation.service.OpenidInfoService;
import com.dascom.operation.service.OpenidStatisticsService;
import com.dascom.operation.utils.ResultVOUtil;
import com.dascom.operation.vo.ResultVO;


@RestController
public class OpenidController {
	
	private static final Logger logger = LogManager.getLogger(OpenidController.class);
	
	@Autowired
	private OpenidInfoService openidInfoService;
	
	@Autowired
	private OpenidStatisticsService openidStatisticsService;
	
	
	
	
	@RequestMapping(value="saveOpenid",method=RequestMethod.POST)
	public ResultVO saveOpenId(HttpServletRequest req,HttpServletResponse resp,@RequestBody JSONObject obj) {
		if(!obj.containsKey("openid")) {
			logger.info("------缺少openid参数------");
			return ResultVOUtil.error(1301);
		}
		if(!obj.containsKey("success")) {
			logger.info("------缺少success参数------");
			return ResultVOUtil.error(1301);
		}
		if(!obj.containsKey("fail")) {
			logger.info("------缺少fail参数------");
			return ResultVOUtil.error(1301);
		}
		String openid = obj.get("openid").toString();
		int printSucced = (int) obj.get("success");
		int printFail = (int) obj.get("fail");
		boolean check = openidInfoService.checkOpenid(openid);
		if(check) {
			//更新
			openidInfoService.update(openid, printSucced, printFail);
			logger.info("------更新成功------");
			logger.info("----更新----openid="+openid+",insertSuccess="+printSucced+",insertFail="+printFail);
			return ResultVOUtil.success();
		}else {
			//插入
			openidInfoService.add(openid, printSucced, printFail);
			logger.info("------新增成功------");
			logger.info("----新增----openid="+openid+",addSuccess="+printSucced+",addFail="+printFail);
			return ResultVOUtil.success();
		}
	}
	
	
	
	
	//获取当前
	@RequestMapping("getOpenidStatisticsList")
	public List<OpenidStatistics> getOpenidStatisticsList() {
		//先统计openid_info
		openidStatisticsService.insertStatistics();
		//返回查询数据
		return openidStatisticsService.getOpenidStatisticsList();
	}
	
	
	//按月查询统计
	@RequestMapping("monthlyStatistics")
	public Map<String,Object> monthlyStatistics(){
		return openidStatisticsService.monthlyStatistics();
	}
	
}
