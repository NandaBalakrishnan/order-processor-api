package com.comp.orderprocessorapi.config;

import com.comp.orderprocessorapi.model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisDbConfig {
	
	@Value("${spring.redis.host}")
	private String redisDbHost;
	
	@Value("${spring.redis.port}")
	private int redisDbPort;
	
	private static final Logger logger = LogManager.getLogger(RedisDbConfig.class);
	
	
	  @Bean 
	  JedisConnectionFactory jedisConnectionFactory() {
		  	RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisDbHost,redisDbPort);
		  	return new JedisConnectionFactory(redisStandaloneConfiguration); 
	  }
	
	@Bean
	RedisTemplate<String, Order> redisTemplate(){
		
		RedisTemplate<String, Order> redisTemplate = new RedisTemplate<>();
		try {
			RedisSerializer<String> stringSerializer = new StringRedisSerializer();
		    JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
		    redisTemplate.setConnectionFactory(jedisConnectionFactory());
		    redisTemplate.setKeySerializer(stringSerializer);
		    redisTemplate.setHashKeySerializer(stringSerializer);
		    redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
		    redisTemplate.setHashValueSerializer(jdkSerializationRedisSerializer);
		    redisTemplate.setEnableTransactionSupport(true);
		    redisTemplate.afterPropertiesSet();
		}
		catch (Exception e) {
			logger.error("Error in Redis DB configuration: "+ e.getMessage());
		}
		
		return redisTemplate;
		
	}

}
