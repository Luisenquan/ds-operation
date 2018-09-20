package com.dascom.operation.utils;

import java.util.Map;

/**
 * 判断HTTP状态码
 * @author Leisenquan
 * @time 2018年8月22日 下午4:25:10
 * @project_name ds-operation
 */
public class CheckStatusCode {
	
	public void getStatusCode(Map<String,Object> resultMap){
		int code = (int) resultMap.get("statusCode");
		if(code >= 400){
			
		}
	}

}
