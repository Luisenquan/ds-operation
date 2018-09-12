package com.dascom.operation.app;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dascom.operation.entity.CollectionInterface;
import com.dascom.operation.entity.OpenidStatistics;
import com.dascom.operation.entity.OperationOpenidStatistics;
import com.dascom.operation.service.InterfaceService;
import com.dascom.operation.service.RedisService;
import com.dascom.operation.utils.AggreationWithResult;
import com.dascom.operation.utils.FormatDate;
import com.dascom.operation.utils.HttpClientUtils;
import com.mongodb.DBObject;

/**
 * 运维平台定时任务类
 * 
 * @author Leisenquan
 * @time 2018年9月12日 上午9:38:26
 * @project_name ds-operation
 */

@Component
public class OperationScheduled {

	private static final Logger logger = LogManager.getLogger(OperationScheduled.class);

	@Value("${statistics_online}")
	private String statisticsOnline;

	@Autowired
	@Qualifier("printLogMongoTemplate")
	MongoTemplate printLogMongoTemplate;

	@Autowired
	@Qualifier("operationMongoTemplate")
	MongoTemplate operationMongoTemplate;

	@Autowired
	private InterfaceService interfaceService;

	@Autowired
	private RedisService redisService;

	private AggreationWithResult result = new AggreationWithResult();

	private static Date getStartTime() {
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		return todayStart.getTime();
	}

	private static Date getEndTime() {
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.set(Calendar.HOUR_OF_DAY, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		return todayEnd.getTime();
	}

	// 请求统计设备时长接口
	@Scheduled(cron = "59 59 23 * * ?")
	public void activeState() {
		String uuid = UUID.randomUUID().toString();
		String jsonPara = "{\"id\":\"" + uuid + "\"}";
		logger.info(jsonPara);
	String headerPara = "{\"Content-Type\":\"application/json\"}";
		logger.info(headerPara);
		Map<String, Object> resultMap = HttpClientUtils.doPost(statisticsOnline, jsonPara, headerPara);
		int statusCode = (int) resultMap.get("statusCode");
		String resultLine = (String) resultMap.get("resultLine");
		if (statusCode < 400) {
			logger.info("-------active定时任务请求成功！查询数据库------");
		} else {
			logger.info("------active定时任务请求失败！错误码：" + statusCode + "------");
			logger.error("------失败原因------" + resultLine);
		}
	}

	@Scheduled(cron = "59 59 23 * * ?")
	public void openidScheduled() {
		// 统计openid总数
		int totalOpenid = printLogMongoTemplate.findAll(OpenidStatistics.class).size();
		// 统计当天新增openid数
		Aggregation aggNewOpenid = Aggregation.newAggregation(
				Aggregation.match(Criteria.where("createDate").lte(getEndTime()).gte(getStartTime())),
				Aggregation.group().count().as("new_openid"));
		DBObject resultNewOpenid = result.getResult(aggNewOpenid, printLogMongoTemplate,
				"collection_openid_statistics");
		int newOpenid = resultNewOpenid == null ? 0 : Integer.parseInt(resultNewOpenid.get("new_openid").toString());

		// 统计当天上线openid数量
		Aggregation aggTodayOpenid = Aggregation.newAggregation(
				Aggregation.match(Criteria.where("lastModifyDate").lte(getEndTime()).gte(getStartTime())),
				Aggregation.group().count().as("today_openid"));
		DBObject resultTodayOpenid = result.getResult(aggTodayOpenid, printLogMongoTemplate,
				"collection_openid_statistics");
		int todayOpenid = resultTodayOpenid == null ? 0: Integer.parseInt(resultTodayOpenid.get("today_openid").toString());

		// 统计总打印数
		Aggregation aggPrintTotal = Aggregation
				.newAggregation(Aggregation.group().sum("printNumber").as("print_total"));
		DBObject resultPrintTotal = result.getResult(aggPrintTotal, printLogMongoTemplate,
				"collection_openid_statistics");
		int printTotal = resultPrintTotal == null ? 0
				: Integer.parseInt(resultPrintTotal.get("print_total").toString());

		String statisticsDate = FormatDate.getTheDate();
		OperationOpenidStatistics operationOpenidStatistics = new OperationOpenidStatistics(statisticsDate, printTotal,
				totalOpenid, todayOpenid, newOpenid);
		operationMongoTemplate.insert(operationOpenidStatistics);
		logger.info("----当前时间：" + new Date() + "----统计openid完成----");
	}

	@Scheduled(cron = "59 59 23 * * ?")
	public void updateRedis() {
		// 查询mongo获取数据
		List<CollectionInterface> interfaceList = interfaceService.getAllInterface();
		try {
			for (CollectionInterface inter : interfaceList) {
				// 刷新redis
				redisService.copyData(inter);
			}
			logger.info("------redis定时刷新成功！------");
		} catch (Exception e) {
			logger.info("------redis定时刷新失败！查看错误日志------");
			logger.error(e.getStackTrace().toString());
		}
	}

}
