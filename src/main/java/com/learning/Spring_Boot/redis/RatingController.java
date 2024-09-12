package com.learning.Spring_Boot.redis;

import com.learning.Spring_Boot.common.Idempotent;
import com.learning.Spring_Boot.entity.Rating;
import com.learning.Spring_Boot.response.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("redis")
public class RatingController {
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private OrderService orderService;

    @Qualifier(value = "RequestScope")
    @Autowired
    private CustomResponse requestScope;

    @Qualifier(value = "PrototypeScope")
    @Autowired
    private CustomResponse prototypeScope;

    @Autowired
    private ApplicationContext context;

    @GetMapping("/rating")
    @Cacheable(value = "ratingList")
    public List<Rating> getAllRating() {
        return ratingRepository.findAll();
    }

    @GetMapping("/rating/{id}")
    @Cacheable(value = "rating", key = "#id", unless = "#result == null")
    public Rating getRatingById(@PathVariable long id) {
        System.out.println(id);
        return ratingRepository.findById(id).orElse(null);
    }

    @PostMapping("/rating")
    @Idempotent
    public Rating saveRating(@RequestBody Rating rating) {
        return ratingRepository.save(rating);
    }

    @PutMapping("/rating")
    @Caching(
            evict = {@CacheEvict(value = "ratingList", allEntries = true)},
            put = {@CachePut(value = "rating", key = "#rating.id")}
    )
    public Rating editRating(@RequestBody Rating rating) {
        return ratingRepository.save(rating);
    }

    @DeleteMapping("/rating/{id}")
    @CacheEvict(value = "rating", key = "#id")
    public Boolean deleteRating(@PathVariable("id") Long id) {
        ratingRepository.deleteById(id);
        return true;
    }

    @GetMapping("/placeOrder")
    public String placeOrder(@RequestParam String orderId) {
        boolean success = orderService.placeOrder(orderId);
        return success ? "Order placed successfully" : "Failed to place order";
    }


    @GetMapping("idempotent")
    public void someMethod() throws Exception {
        throw new Exception("Custom exception");
    }

    public static int count = 0;

    @GetMapping("prototype")
    public CustomResponse prototypeCheck() throws Exception {
//        CustomResponse response = context.getBean(CustomResponse.class);
        count++;
        prototypeScope.setStatus(HttpStatus.OK);
        System.out.println("Response object: " + prototypeScope.hashCode());
        prototypeScope.setErrorMessage(String.valueOf(count));
        Thread.sleep(5000);
        return prototypeScope;
    }

    @GetMapping("request")
    public CustomResponse check() throws InterruptedException {
        count++;
//        CustomResponse response = context.getBean(CustomResponse.class);
        requestScope.setStatus(HttpStatus.OK);
        requestScope.setErrorMessage(String.valueOf(count));
        Thread.sleep(5000);
        return requestScope;
    }

}