package com.dascom.operation.utils;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.dascom.operation.entity.CollectionInterface;

/**
 * mongo 拼接请求地址
 * 
 * @author Leisenquan
 * @time 2018年8月22日 下午2:49:47
 * @project_name ds-operation
 */

@Component
public class MongoJoinPara {
	
	private static final Logger logger = LogManager.getLogger(MongoJoinPara.class);
	
	Map<String,Object> resultMap = null;

	public Map<String, Object> mongoToHttpClient(CollectionInterface inter) {
		
		String requestUrl = inter.getRequestUrl();
		
		//logger.info("------接口地址------："+url);
		String jsonPara = inter.getJsonParameter();
		jsonPara = jsonPara==null?"":jsonPara;
		String headerPara = inter.getHeaderParameter();
		headerPara = headerPara==null?"":headerPara;
		String method = inter.getMethod().toLowerCase(); //转小写
		switch (method) {
			case "get":
				//logger.info("------调用get请求----");
				resultMap = HttpClientUtils.doGet(requestUrl, headerPara);
				break;
			case "post":
				//logger.info("------调用post请求----");
				resultMap = HttpClientUtils.doPost(requestUrl, jsonPara, headerPara);
				//logger.info("------请求参数------："+jsonPara);
				break;
			case "delete":
				//logger.info("------调用delete请求----");
				resultMap = HttpClientUtils.doDelete(requestUrl, headerPara);
				break;
			case "patch":
				//logger.info("------调用patch请求----");
				resultMap = HttpClientUtils.doPatch(requestUrl, jsonPara, headerPara);
				//logger.info("------请求参数------："+jsonPara);
				break;
		}
		return resultMap;
	}

}
