package com.learning.Spring_Boot.customException;

public class RatingRollbackException extends RuntimeException{
    public RatingRollbackException(String errorMessage){
        super(errorMessage);
    }
}
