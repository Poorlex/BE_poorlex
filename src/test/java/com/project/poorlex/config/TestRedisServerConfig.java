package com.project.poorlex.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import redis.embedded.RedisServer;

@TestConfiguration
public class TestRedisServerConfig {
	private final RedisServer redisServer;

	public TestRedisServerConfig(@Value("${spring.redis.port}") int redisPort) {
		this.redisServer = RedisServer.builder()
			.port(redisPort)
			.setting("maxmemory 10M").build();
	}

	@PostConstruct
	public void startRedis() {
		this.redisServer.start();
	}

	@PreDestroy
	public void stopRedis() {
		this.redisServer.stop();
	}
}
