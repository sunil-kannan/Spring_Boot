package com.learning.Spring_Boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.context.ConfigurationWarningsApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@org.springframework.boot.autoconfigure.SpringBootApplication(exclude = { JacksonAutoConfiguration.class})
public class SpringBootApplication {

    public static void main(String[] args) {
        /*
          ApplicationContext represents the Spring IoC container that holds all the beans created by the application.
          It is responsible for instantiating, configuring, and creating the beans.
          Additionally, it gets the beans' information from configuration metadata provided in XML or Java.
         */
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringBootApplication.class, args);
        System.out.println(applicationContext);
        // closing application context
//        applicationContext.close();
    }

}
