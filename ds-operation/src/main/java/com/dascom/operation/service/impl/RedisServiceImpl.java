package com.dascom.operation.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dascom.operation.service.RedisService;
import com.dascom.operation.utils.ObjectToMap;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

@Component("redisService")
public class RedisServiceImpl implements RedisService {

	private static final Logger logger = LogManager.getLogger(RedisServiceImpl.class);

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	
	@Autowired
	@Qualifier("interfaceRedis")
	private  RedisConnectionFactory interfaceRedis;
	
	@Autowired
	@Qualifier("printerRedis")
	private  RedisConnectionFactory printerRedis;
	
	private  Jedis getRedis() {
		Jedis redis = (Jedis) interfaceRedis.getConnection().getNativeConnection();
		return redis;
	}
	
	private Jedis printerRedis() {
		Jedis redis = (Jedis) printerRedis.getConnection().getNativeConnection();
		return redis;
	}


	@Override
	public Set<String> getAllkey() {

		return redisTemplate.keys("*");
	}

	// 获取所有hash中的key和对应的map
	@Override
	public Map<String, Map<Object, Object>> getAllHash() {
		Map<String, Map<Object, Object>> resultMap = new HashMap<String, Map<Object, Object>>();
		// 获取所有的key
		Set<String> allKeys = redisTemplate.keys("*");
		// 获取key对应的map
		for (String key : allKeys) {
			Map<Object, Object> result = redisTemplate.opsForHash().entries(key);
			resultMap.put(key, result);
		}

		return resultMap;
	}
	
	

	@Override
	public void copyData(Object obj) throws Exception {
		// 将obj转为map
		String str = "";
		Map<String, Object> resultMap = ObjectToMap.objToMap(obj);
		for (String key : resultMap.keySet()) {
			if ((resultMap.get(key) == null)) {
				resultMap.put(key, str);
			}
		}
		String key = (String) resultMap.get("interfaceName");
		// 存入redis
		redisTemplate.opsForHash().putAll(key, resultMap);
	}

	//Use redis pipeline
	public Map<String, Map<String, String>> hgetByPipeline() {
		Jedis redis = getRedis();
		Pipeline p = redis.pipelined();
		Set<String> keys = redis.keys("*");
		Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
		Map<String, Response<Map<String, String>>> responses = new HashMap<String, Response<Map<String, String>>>(keys.size());
		//long start = System.currentTimeMillis();
		for (String key : keys) {
			responses.put(key, p.hgetAll(key));
		}
		p.sync();
		for (String k : responses.keySet()) {
			result.put(k, responses.get(k).get());
		}
	
		redis.disconnect();
		return result;
	}


	@Override
	public Map<String, String> getPrinterState() {
		//查出所有数据
		Jedis redis = printerRedis();
		Pipeline pipeline = redis.pipelined();
		Set<String> key = redis.keys("status");
		Map<String, Response<Map<String, String>>> responses = new HashMap<String, Response<Map<String, String>>>(key.size());
		Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
		Map<String,String> printerStatus = new HashMap<String,String>();
		for(String k : key) {
			responses.put(k, pipeline.hgetAll(k));
		}
		pipeline.sync();
		for(String res : responses.keySet()) {
			result.put(res,responses.get(res).get());
		}
		redis.disconnect();
		Map<String,String> printers = result.get("status");
		for(String id :printers.keySet()) {
			JSONObject obj = JSON.parseObject(printers.get(id));
			String status = obj.get("main").toString();
			printerStatus.put(id, status);
		}
		return printerStatus;
	}


	


}
