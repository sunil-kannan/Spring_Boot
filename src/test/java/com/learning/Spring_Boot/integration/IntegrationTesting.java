package com.learning.Spring_Boot.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.Spring_Boot.redis.entity.Rating;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureMockMvc
class IntegrationTesting {

    @Container
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:16.4");

    @Autowired
    private MockMvc mockMvc;

    @DynamicPropertySource
    static void DynamicConfiguration(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }
    @Test
    @Order(1)
    void getAllRating() throws Exception {
        Thread.sleep(5000);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/redis/rating")
                        .contentType("application/json")
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString();
        System.out.println("Response: " + responseContent);
    }

    @Test
    @Commit
    @Order(2)  // Ensures this test runs first
    @Transactional
    @Rollback(false)
    void testSaveRating() throws Exception {
        Rating rating = Rating.builder()
                .rating(3).review("good").userid("u1").username("Sunil").build();
        mockMvc.perform(MockMvcRequestBuilders.post("/redis/rating")
                .contentType("application/json")
                .content(convertRatingToJson(rating))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @Order(2)
    void getAllRating1() throws Exception {
        Thread.sleep(5000);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/redis/rating")
                        .contentType("application/json")
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString();
        System.out.println("Response: " + responseContent);
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String convertRatingToJson(Rating rating) {
        try {
            return objectMapper.writeValueAsString(rating);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting Rating to JSON", e);
        }
    }

}
