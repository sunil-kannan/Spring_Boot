package com.learning.Spring_Redis.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
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
