package com.learning.Spring_Boot.swagger;

import com.learning.Spring_Boot.redis.entity.Rating;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController("swaggerController")
@RequestMapping("swagger")
public class Controller {
    private static final String SWAGGER_TAG = "Swagger API";

    @GetMapping("/api1")
    @Operation(summary = "API 1", description = "Swagger Api 1", tags = {SWAGGER_TAG})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "create credential successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Rating.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true)))})
    public String api1() {
        return "Swagger Api 1";
    }

    @PostMapping("/api2")
    @Operation(
            summary = "Create an Rating",
            description = "This method is used to save the rating",
            tags = {"rating"},
            requestBody = @RequestBody(
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Rating.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Item created successfully", content = @Content(schema = @Schema(implementation = Rating.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(hidden = true)))
            })
    public Rating api2(@org.springframework.web.bind.annotation.RequestBody Rating rating) {
        return rating;
    }


}
