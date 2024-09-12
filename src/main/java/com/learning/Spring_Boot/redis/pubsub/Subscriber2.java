package com.learning.Spring_Boot.redis.pubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class Subscriber2 implements MessageListener {
    Logger logger = LoggerFactory.getLogger(Subscriber1.class);
    @Override
    public void onMessage(Message message, byte[] pattern) {
        logger.info("Consumed message from Subscriber2: {}", message);
    }
}
