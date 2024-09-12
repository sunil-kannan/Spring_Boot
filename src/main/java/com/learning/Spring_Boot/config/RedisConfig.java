package com.learning.Spring_Boot.config;

import com.learning.Spring_Boot.redis.pubsub.Subscriber1;
import com.learning.Spring_Boot.redis.pubsub.Subscriber2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Value("${spring.cache.host}")
    private String redisHost;

    @Value("${spring.cache.port}")
    private int redisPort;

    // Creates a Redis connection factory using Lettuce, configured to connect to localhost on port 6379.
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisHost, redisPort);
        return new LettuceConnectionFactory(configuration);
    }

    // Defines a RedisTemplate for interacting with Redis. The template is configured with a custom serializer.
    @Bean
    public RedisTemplate<String, Object> template() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class)); // Serializes objects to strings.
        return template;
    }

    // Defines the first Redis channel topic named "pubsub1:spring-redis_cache".
    @Bean
    public ChannelTopic topicOne() {
        return new ChannelTopic("pubsub1:spring-redis_cache");
    }

    // Defines the second Redis channel topic named "pubsub2:spring-redis_cache".
    @Bean
    public ChannelTopic topicTwo() {
        return new ChannelTopic("pubsub2:spring-redis_cache");
    }

    // Creates a MessageListenerAdapter for Subscriber1, which will handle messages sent to topicOne.
    @Bean
    public MessageListenerAdapter messageListenerAdapter1() {
        return new MessageListenerAdapter(new Subscriber1());
    }

    // Creates a MessageListenerAdapter for Subscriber2, which will handle messages sent to both topicOne and topicTwo.
    @Bean
    public MessageListenerAdapter messageListenerAdapter2() {
        return new MessageListenerAdapter(new Subscriber2());
    }

    // Configures the RedisMessageListenerContainer, which is responsible for managing the message listeners.
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();

        // Sets the connection factory for the container.
        container.setConnectionFactory(redisConnectionFactory());

        /**
         * Registers messageListenerAdapter1 with topicOne and topicTwo
         * Subscriber1 and Subscriber2 will receive messages published to topicOne.
         */
        container.addMessageListener(messageListenerAdapter1(), topicOne());
        container.addMessageListener(messageListenerAdapter2(), topicOne());

        // Registers messageListenerAdapter2 with topicTwo.
        // Subscriber2 will receive messages published to topicTwo as well.
        container.addMessageListener(messageListenerAdapter2(), topicTwo());

        return container;
    }
}
