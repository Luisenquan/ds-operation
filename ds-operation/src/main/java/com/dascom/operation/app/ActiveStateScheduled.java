package com.dascom.operation.app;

import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dascom.operation.utils.HttpClientUtils;

@Component
public class ActiveStateScheduled {

	private static final Logger logger = LogManager.getLogger(ActiveStateScheduled.class);
	
	@Value("${statistics_online}")
	private String statisticsOnline;

	// 请求统计设备时长接口
	@Scheduled(cron="59 59 23 * * ?") //每天23点59分59秒更新
	public void getOnline() {
		String uuid = UUID.randomUUID().toString();
		String jsonPara = "{\"id\":\"" + uuid + "\"}";
		logger.info(jsonPara);
		String headerPara = "{\"Content-Type\":\"application/json\"}";
		logger.info(headerPara);
		Map<String, Object> resultMap = HttpClientUtils.doPost(statisticsOnline, jsonPara, headerPara);
		int statusCode = (int) resultMap.get("statusCode");
		String resultLine = (String) resultMap.get("resultLine");
		if (statusCode < 400) {
			logger.info("-------请求成功！查询数据库------");
		} else {
			logger.error("------请求失败！错误码：" + statusCode + "------");
			logger.error("------失败原因------" + resultLine);
		}
	}

}
