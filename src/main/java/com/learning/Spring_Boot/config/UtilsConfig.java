package com.learning.Spring_Boot.config;

import com.learning.Spring_Boot.response.CustomResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class UtilsConfig {

    @Bean
    @RequestScope
    public CustomResponse RequestScope(){
        return new CustomResponse();
    }

    @Bean
    @Scope(value = "prototype")
    public CustomResponse PrototypeScope(){
        return new CustomResponse();
    }


}
