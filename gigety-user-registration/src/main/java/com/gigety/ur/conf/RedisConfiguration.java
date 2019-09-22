package com.gigety.ur.conf;

import java.time.Duration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
@EnableCaching
public class RedisConfiguration {

	private final Environment env;

	public RedisConfiguration(Environment env) {
		super();
		this.env = env;
	}
	
	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration redisConf = new RedisStandaloneConfiguration();
		redisConf.setHostName(env.getProperty("spring.redis.host"));
		redisConf.setPort(Integer.parseInt(env.getProperty("spring.redis.port")));
		redisConf.setPassword(RedisPassword.of(env.getProperty("spring.redis.password")));
		return new LettuceConnectionFactory(redisConf);
	}
	
	@Bean
	public RedisCacheConfiguration cacheCOnfiguration() {
		RedisCacheConfiguration cacheConf = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofSeconds(600))
				.disableCachingNullValues();
		return cacheConf;
	}
	
	@Bean
	public RedisCacheManager cacheManager() {
		RedisCacheManager rcm = RedisCacheManager.builder(redisConnectionFactory())
				.cacheDefaults(cacheCOnfiguration())
				.transactionAware()
				.build();
		return rcm;
	}
}
