package com.learning.Spring_Boot.Exceptionhandling;

import com.learning.Spring_Boot.customException.RatingRollbackException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class SQLException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RatingRollbackException.class)
    public ResponseEntity<com.learning.Spring_Boot.response.ExceptionResponse> handleUserNotFoundException(Exception ex){
        com.learning.Spring_Boot.response.ExceptionResponse response = new com.learning.Spring_Boot.response.ExceptionResponse();
        response.setErrorCode("GUI101");
        response.setStatus(HttpStatus.NOT_FOUND);
        response.setErrorMessage("User not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
