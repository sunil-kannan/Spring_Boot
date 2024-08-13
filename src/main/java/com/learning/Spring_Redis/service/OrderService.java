package com.learning.Spring_Redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private DistributedLockService distributedLockService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String STOCK_KEY = "stock:product1";

    public boolean placeOrder(String orderId) {
        // Attempt to acquire a lock for stock reservation
        boolean lockAcquired = distributedLockService.acquireLock(STOCK_KEY, 5000); // Lock for 5 seconds

        if (!lockAcquired) {
            // Handle lock acquisition failure
            System.out.println("Could not acquire lock for stock reservation.");
            return false;
        }
        try {
            // Fetch and convert stock value from Redis
            String stockString = (String) redisTemplate.opsForValue().get(STOCK_KEY);
            Integer stock = null;
            if (stockString != null) {
                try {
                    stock = Integer.parseInt(stockString);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid stock value in Redis.");
                    return false;
                }
            }

            if (stock != null && stock > 0) {
                // Reserve stock
                redisTemplate.opsForValue().decrement(STOCK_KEY);
                System.out.println("Order placed successfully.");
                return true;
            } else {
                System.out.println("Stock not available.");
                return false;
            }
        } finally {
            // Release the lock after stock reservation process is complete
            distributedLockService.releaseLock(STOCK_KEY);
        }
    }
}
