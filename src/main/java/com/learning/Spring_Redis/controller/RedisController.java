package com.learning.Spring_Redis.controller;

import com.learning.Spring_Redis.entity.Rating;
import com.learning.Spring_Redis.repository.RatingRepository;
import com.learning.Spring_Redis.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RedisController {
    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private OrderService orderService;


    @GetMapping("/rating")
    @Cacheable(value = "ratingList")
    public List<Rating> getAllRating(){
        return ratingRepository.findAll();
    }

    @GetMapping("/rating/{id}")
    @Cacheable(value = "rating", key = "#id", unless = "#result == null")
    public Rating getRatingById(@PathVariable long id) {
        System.out.println(id);
        return ratingRepository.findById(id).orElse(null);
    }

    @PostMapping("/rating")
    public Rating saveRating(@RequestBody Rating rating){
        return ratingRepository.save(rating);
    }

    @PutMapping("/rating")
    @Caching(
            evict = {@CacheEvict(value = "ratingList", allEntries = true)},
            put = {@CachePut(value = "rating", key = "#rating.id")}
    )
    public Rating editRating(@RequestBody Rating rating){
        return ratingRepository.save(rating);
    }

    @DeleteMapping("/rating/{id}")
    @CacheEvict(value = "rating", key = "#id")
    public Boolean deleteRating(@PathVariable("id")Long id){
        ratingRepository.deleteById(id);
        return true;
    }

    @GetMapping("/placeOrder")
    public String placeOrder(@RequestParam String orderId) {
        boolean success = orderService.placeOrder(orderId);
        return success ? "Order placed successfully" : "Failed to place order";
    }

}
