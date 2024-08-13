package com.learning.Spring_Redis.controller.pubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class Subscriber1 implements MessageListener {
    Logger logger = LoggerFactory.getLogger(Subscriber1.class);
    @Override
    public void onMessage(Message message, byte[] pattern) {
        logger.info("Consumed message from Subscriber1: {}", message);
    }
}