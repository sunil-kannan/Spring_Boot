package com.learning.Spring_Boot.redis.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Rating implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "rev_seq")
    @SequenceGenerator(name = "rev_seq",sequenceName = "rev_seq",allocationSize = 1)
    private Long id;
    private String review;
    @Column(nullable = false)
    private float rating;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String userid;

}
