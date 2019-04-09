package com.feng.wenda.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfiguration {

    @Bean("jedis.config")
    public JedisPoolConfig jedisPoolConfig(
            @Value("${spring.redis.jedis.pool.min-idle}") int minIdle,
            @Value("${spring.redis.jedis.pool.max-idle}") int maxIdle,
            @Value("${spring.redis.jedis.pool.max-wait}") int maxWaitMillis) {

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMinIdle(minIdle);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        return config;
    }

    @Bean
    public JedisPool jedisPool(
            @Qualifier("jedis.config") JedisPoolConfig config,
            @Value("${spring.redis.host}") String host,
            @Value("${spring.redis.port}") int port) {
        return new JedisPool(config, host, port);
    }
}
