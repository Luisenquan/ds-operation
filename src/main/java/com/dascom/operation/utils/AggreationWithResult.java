package com.dascom.operation.utils;

import java.util.Iterator;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * mongodb 聚合返回result
 * @author Leisenquan
 * @date 2018年8月17日
 * @project_name ds-operation
 */
public class AggreationWithResult {
	
	public  DBObject getResult(Aggregation agg,MongoTemplate template,String collectionName){
		DBObject obj = null;
		AggregationResults<BasicDBObject> result = template.aggregate(agg, collectionName,BasicDBObject.class);
		for(Iterator<BasicDBObject> iterator=result.iterator();iterator.hasNext();){
			obj = iterator.next();
		}
		return obj;
	}

}
