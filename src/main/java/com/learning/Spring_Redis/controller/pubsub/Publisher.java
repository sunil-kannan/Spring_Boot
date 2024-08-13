package com.learning.Spring_Redis.controller.pubsub;

import com.learning.Spring_Redis.entity.Rating;
import com.learning.Spring_Redis.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Publisher {

    @Autowired
    private RatingRepository ratingRepository;


    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic topicOne;
    private final ChannelTopic topicTwo;

    public Publisher(RedisTemplate<String, Object> redisTemplate,
                                   @Qualifier("topicOne") ChannelTopic topicOne,
                                   @Qualifier("topicTwo") ChannelTopic topicTwo) {
        this.redisTemplate = redisTemplate;
        this.topicOne = topicOne;
        this.topicTwo = topicTwo;
    }


    @PostMapping("publish1")
    public String publish1(@RequestBody Rating rating){
        redisTemplate.convertAndSend(topicOne.getTopic(), rating.toString());
        return "Event published";
    }

    @PostMapping("publish2")
    public String publish2(@RequestBody Rating rating){
        redisTemplate.convertAndSend(topicTwo.getTopic(), rating.toString());
        return "Event published";
    }

}
