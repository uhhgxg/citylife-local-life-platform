package com.hmdp.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class  RedissonConfig {

    @Bean
    public RedissonClient redissonClient(){
        String host = System.getenv().getOrDefault("REDIS_HOST", "127.0.0.1");
        String port = System.getenv().getOrDefault("REDIS_PORT", "6379");
        String password = System.getenv().getOrDefault("REDIS_PASSWORD", "");

        Config config = new Config();
        org.redisson.config.SingleServerConfig server = config.useSingleServer()
                .setAddress("redis://" + host + ":" + port);
        if (!password.isEmpty()) {
            server.setPassword(password);
        }

        // 创建RedissonClient对象
        return Redisson.create(config);
    }
}
