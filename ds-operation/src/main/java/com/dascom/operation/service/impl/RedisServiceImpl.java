package com.dascom.operation.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

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
	private  RedisConnectionFactory connectionFactory;
	
	private  Jedis getRedis() {
		Jedis redis = (Jedis) connectionFactory.getConnection().getNativeConnection();
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
		//long end = System.currentTimeMillis();
		//System.out.println("hgetAll with pipeline used [" + (end - start) / 1000 + "] seconds ..");
		redis.disconnect();
		return result;
	}


	


}
