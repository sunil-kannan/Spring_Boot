package com.learning.Spring_Boot.controller;

import com.learning.Spring_Boot.entity.Rating;
import com.learning.Spring_Boot.redis.RatingController;
import com.learning.Spring_Boot.redis.RatingRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RatingControllerTest {
    @InjectMocks
    private RatingController redisController;
    @Mock
    private RatingRepository ratingRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetAllRatings_Valid() {
        List<Rating> mockRatings = Arrays.asList(
                new Rating(1l,"review", 3.5f, "John","s4"),
                new Rating(2l,"review", 4.5f, "Don","s5")
        );
        when(ratingRepository.findAll()).thenReturn(mockRatings); // mocking repository which we called from controller
        List<Rating> ratings = redisController.getAllRating();
        verify(ratingRepository, times(1)).findAll(); // the method should be invoked only one time in the controller
        assertNotNull(ratings);
        assertEquals(2, ratings.size());
        assertEquals(3.5f, ratings.get(0).getRating());
        assertEquals("John", ratings.get(0).getUsername());
    }

    @Test
    public void testGetAllRatings_NoRatings() {
        when(ratingRepository.findAll()).thenReturn(Collections.emptyList());
        List<Rating> ratings = redisController.getAllRating();
        verify(ratingRepository, times(1)).findAll();
        assertTrue(ratings.isEmpty());
    }

    @Test
    public void testGetAllRatings_RepositoryException() {
        when(ratingRepository.findAll()).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> {
            redisController.getAllRating();
        });
    }





//    @AfterEach
//    void tearDown() {
//        System.out.println("AfterEach Method");
//    }
//
//    @AfterAll
//    static void afterAll() {
//        System.out.println("AfterALl Method");
//    }



//    @Test
//    void getRatingById() {
//    }
//
//    @Test
//    void saveRating() {
//    }
//
//    @Test
//    void editRating() {
//    }
//
//    @Test
//    void deleteRating() {
//    }
//
//    @Test
//    void placeOrder() {
//    }
//
//    @Test
//    void someMethod() {
//    }
//
//    @Test
//    void prototypeCheck() {
//    }
//
//    @Test
//    void check() {
//    }
}