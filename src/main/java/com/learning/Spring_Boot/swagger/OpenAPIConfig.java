package com.learning.Spring_Boot.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 1. Add openapi dependency
 * 2. Add swagger and openApi properties in .properties or .yaml file
 * 3. Add OpenAPI and GroupedOpenApi config file
 */
@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI baseOpenAPI(){
        Server server = new Server();
        server.setUrl("http://localhost:8085");
        server.setDescription("Development");

        Contact myContact = new Contact();
        myContact.setName("Jane Doe");
        myContact.setEmail("your.email@gmail.com");

        Info info = new Info()
                .title("Spring Project OpenAPI Docs example")
                .version("1.0.0").description("Documentation description")
                .contact(myContact);

        return new OpenAPI().info(info).servers(List.of(server));
    }

    @Bean
    public GroupedOpenApi redisApi() {
        return GroupedOpenApi.builder()
                .group("redis")
                .pathsToMatch("/redis/**") // Customize the path if needed
                .build();
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("swagger")
                .pathsToMatch("/swagger/**") // Customize the path if needed
                .build();
    }

}
