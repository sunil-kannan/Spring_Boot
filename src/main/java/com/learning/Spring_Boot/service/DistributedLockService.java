package com.learning.Spring_Boot.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class DistributedLockService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String LOCK_PREFIX = "lock:";

    public boolean acquireLock(String key, long timeout) {
        String lockKey = LOCK_PREFIX + key;
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(lockKey, "locked", timeout, TimeUnit.MILLISECONDS));
    }

    public void releaseLock(String key) {
        String lockKey = LOCK_PREFIX + key;
        redisTemplate.delete(lockKey);
    }

    private static final String STOCK_KEY = "stock:product1";

    @PostConstruct
    public void initializeStock() {
        // Set initial stock value to 10
        redisTemplate.opsForValue().set(STOCK_KEY, 10);
        System.out.println("Stock initialized to 10.");
    }




}
