package com.learning.Spring_Boot.controller;

import com.learning.Spring_Boot.redis.entity.Rating;
import com.learning.Spring_Boot.redis.RatingController;
import com.learning.Spring_Boot.redis.RatingRepository;
import com.learning.Spring_Boot.redis.entity.RatingDto;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RatingControllerTest {
    @InjectMocks
    private RatingController redisController;
    @Spy
    private ModelMapper spyModelMapper;
    @Spy
    private RatingController spyController;
    @Mock
    private RatingRepository ratingRepository;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        spyModelMapper.typeMap(Rating.class, RatingDto.class).addMappings(mapper -> {
            mapper.map(Rating::getUsername, RatingDto::setName);
        });
    }
    @Test
    public void testMapper() {
        Rating mockRating = new Rating(1L, "review", 3.5f, "John", "s4");
        RatingDto mockRatingDto = new RatingDto("review", 3.5f, "John", "s4");
        RatingDto result = spyModelMapper.map(mockRating, RatingDto.class);
        assertEquals(mockRatingDto, result);
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



}