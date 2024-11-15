package org.example.batch.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisLockConfig {

	@Value("${spring.data.redis.host}")
	private String host;

	@Value("${spring.data.redis.port}")
	private int port;

	@Bean
	public RedissonClient redissonClient() {
		// Redis의 기본 설정 (standalone 모드 사용 예제)
		Config config = new Config();
		config.useSingleServer()
			.setAddress("redis://" + host + ":" + port) // Redis 주소
			.setConnectionPoolSize(64)
			.setConnectionMinimumIdleSize(24)
			.setIdleConnectionTimeout(10000)
			.setConnectTimeout(10000)
			.setTimeout(3000)
			.setRetryAttempts(3)
			.setRetryInterval(1500);

		return Redisson.create(config);
	}
}
