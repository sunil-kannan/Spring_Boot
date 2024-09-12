package com.learning.Spring_Boot.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ExceptionResponse {
    private HttpStatus status;
    private String errorMessage;
    private String errorCode;
}
