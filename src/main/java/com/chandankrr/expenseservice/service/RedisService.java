package com.chandankrr.expenseservice.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isDuplicate(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void storeKey(String key, Duration ttl) {
        redisTemplate.opsForValue().set(key, true, ttl);
    }
}
