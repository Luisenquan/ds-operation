package com.dascom.operation.utils;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RedisJoinPara {
	
	private static final Logger logger = LogManager.getLogger(RedisJoinPara.class);
	
	Map<String,Object> resultMap = null;
	
	public Map<String,Object> redisToHttpClient(Map<String,String> interMap,String domain){
		String portName = (String)interMap.get("portName");
		String requestUrl = (String)interMap.get("requestUrl");
		String urlPara = (String)interMap.get("urlParameter");
		urlPara = urlPara==null?"":urlPara;
		String jsonPara = (String)interMap.get("jsonParameter");
		jsonPara = jsonPara==null?"":jsonPara;
		String headerPara = (String)interMap.get("headerParameter");
		headerPara = headerPara==null?"":headerPara;
		String method = (String)interMap.get("method");
		String url = domain+portName+requestUrl+urlPara;
		//logger.info("------接口地址------："+url);
		switch (method){
			case "get":
				//logger.info("------调用get请求----");
				resultMap = HttpClientUtils.doGet(url, headerPara);
				break;
			case "post":
				//logger.info("------调用post请求----");
				resultMap = HttpClientUtils.doPost(url, jsonPara, headerPara);
				//logger.info("------请求参数------："+jsonPara);
				break;
			case "delete":
				//logger.info("------调用delete请求----");
				resultMap = HttpClientUtils.doDelete(url, headerPara);
				break;
			case "patch":
				//logger.info("------调用patch请求----");
				resultMap = HttpClientUtils.doPatch(url, jsonPara, headerPara);
				//logger.info("------请求参数------："+jsonPara);
				break;
		}
		return resultMap;
	}

}
