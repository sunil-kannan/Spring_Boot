package com.learning.Spring_Boot.controller;

import com.learning.Spring_Boot.redis.RatingRepository;
import com.learning.Spring_Boot.redis.entity.Rating;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
//@NoArgsConstructor
public class Check{
    private final ConcurrentDataService concurrentDataService;

    @GetMapping("/concurrent-save")
    public String saveConcurrently() {
    Rating rating = new Rating(null, "good",4f,"sunil","u1");
    Rating rating1 = new Rating(null, "bad", 5f, "godson","u2");
        CompletableFuture<Void> thread1 = concurrentDataService.saveDataWithRollback(rating, true); // This will simulate rollback
        CompletableFuture<Void> thread2 = concurrentDataService.saveDataWithRollback(rating1, false); // This will not rollback

        // Wait for both threads to complete
        CompletableFuture.allOf(thread1, thread2).join();

        return "Data saving completed!";
    }
}

@Service
@AllArgsConstructor
class ConcurrentDataService {

    private final DataService dataService ;

    @Async
    public CompletableFuture<Void> saveDataWithRollback(Rating data, boolean shouldRollback) {
        try {
           dataService.saveData(data);
            if (shouldRollback) {
                throw new RuntimeException("Simulated rollback");
            }
        } catch (RuntimeException e) {
            // Handle rollback simulation or logging
            System.out.println("Exception occurred: " + e.getMessage());
            // Ensure proper handling or cleanup
        }
        return CompletableFuture.completedFuture(null);
    }



}



@Service
@AllArgsConstructor
 class DataService {

    private final RatingRepository dataRepository;

    @Transactional
    public void saveData(Rating data) {
        System.out.println("Data"+data.getUsername());

        try{
            Thread.sleep(1000);
            Rating rating = dataRepository.save(data);
            System.out.println(rating.getId());
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(Objects.equals(data.getReview(), "good")){
            throw new RuntimeException();
        }
    }
}