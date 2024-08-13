package com.learning.Spring_Redis.repository;

import com.learning.Spring_Redis.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RatingRepository extends JpaRepository<Rating, Long> {

}
