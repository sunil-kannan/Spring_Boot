package com.learning.Spring_Boot.redis;

import com.learning.Spring_Boot.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {

}
