package com.dascom.operation.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import com.fasterxml.jackson.databind.deser.std.JdkDeserializers;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/** * @author  作者 E-mail: 
* @date 创建时间：2018年8月24日 上午6:28:04 
* @version 1.0 
* @parameter  
* @since  
* @return  */

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport{
	
	private static final Logger logger = LogManager.getLogger(RedisConfig.class);

	@Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.pool.max-wait}")
    private long maxWaitMillis;

    @Value("${spring.redis.password}")
    private String password;
    
    @Value("${spring.redis.interface.database}")
    private int interfacedb;
    
    @Value("${spring.redis.printer.database}")
    private int printerdb;
    
   
    @Primary
    @Bean(name="interfaceRedis")
    public RedisConnectionFactory redisConnectionFactory() {
    	JedisPoolConfig poolConfig = new JedisPoolConfig();
    	poolConfig.setMaxIdle(maxIdle);
    	poolConfig.setMaxWaitMillis(maxWaitMillis);
    	JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
    	jedisConnectionFactory.setHostName(host);
    	jedisConnectionFactory.setPort(port);
    	jedisConnectionFactory.setPassword(password);
    	jedisConnectionFactory.setDatabase(interfacedb);
    	return jedisConnectionFactory;
    }
    
    
    @Bean(name="printerRedis")
    public RedisConnectionFactory printerConnectionFactory() {
    	JedisPoolConfig poolConfig = new JedisPoolConfig();
    	poolConfig.setMaxIdle(maxIdle);
    	poolConfig.setMaxWaitMillis(maxWaitMillis);
    	JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
    	jedisConnectionFactory.setHostName(host);
    	jedisConnectionFactory.setPort(port);
    	jedisConnectionFactory.setPassword(password);
    	jedisConnectionFactory.setDatabase(printerdb);
    	return jedisConnectionFactory;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
