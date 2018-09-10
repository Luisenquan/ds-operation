package com.dascom.operation.app;

import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dascom.operation.entity.OpenidStatistics;
import com.dascom.operation.entity.OperationOpenidStatistics;
import com.dascom.operation.utils.AggreationWithResult;
import com.dascom.operation.utils.FormatDate;
import com.mongodb.DBObject;


@Component
public class OperationOpenidScheduled {
	
	private static final Logger logger = LogManager.getLogger(OperationOpenidScheduled.class);
	
	@Autowired
	@Qualifier("printLogMongoTemplate")
	MongoTemplate printLogMongoTemplate;
	
	@Autowired
	@Qualifier("operationMongoTemplate")
	MongoTemplate operationMongoTemplate;
	
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
    
    @Scheduled(cron="59 59 23 * * ?")   //每天23点59分59秒更新
    public void addOperationOpenid() {
		//统计openid总数
		int totalOpenid = printLogMongoTemplate.findAll(OpenidStatistics.class).size();
		//统计当天新增openid数
		Aggregation aggNewOpenid = Aggregation.newAggregation(
				Aggregation.match(Criteria.where("createDate").lte(getEndTime()).gte(getStartTime())),
				Aggregation.group().count().as("new_openid")
				);
		DBObject resultNewOpenid = result.getResult(aggNewOpenid, printLogMongoTemplate, "collection_openid_statistics");
		int newOpenid = resultNewOpenid==null?0:Integer.parseInt(resultNewOpenid.get("new_openid").toString());
		
		//统计当天上线openid数量
		Aggregation aggTodayOpenid = Aggregation.newAggregation(
				Aggregation.match(Criteria.where("lastModifyDate").lte(getEndTime()).gte(getStartTime())),
				Aggregation.group().count().as("today_openid")
				);
		DBObject resultTodayOpenid = result.getResult(aggTodayOpenid, printLogMongoTemplate, "collection_openid_statistics");
		int todayOpenid = resultTodayOpenid==null?0:Integer.parseInt(resultTodayOpenid.get("today_openid").toString());
		
		//统计总打印数
		Aggregation aggPrintTotal = Aggregation.newAggregation(
				Aggregation.group().sum("printNumber").as("print_total")
				);
		DBObject resultPrintTotal = result.getResult(aggPrintTotal, printLogMongoTemplate, "collection_openid_statistics");
		int printTotal = resultPrintTotal==null?0:Integer.parseInt(resultPrintTotal.get("print_total").toString());
		
		String statisticsDate = FormatDate.getTheDate();
		OperationOpenidStatistics operationOpenidStatistics = new OperationOpenidStatistics(statisticsDate, printTotal, totalOpenid, totalOpenid, newOpenid);
		operationMongoTemplate.insert(operationOpenidStatistics);
		logger.info("----当前时间："+new Date()+"----统计openid完成----");
	}
 
}
