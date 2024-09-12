package com.learning.Spring_Boot.response;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;


@Data
@JsonIncludeProperties({"status","errorMessage","data"})
public class CustomResponse implements Serializable{
    public CustomResponse(){
        System.out.println("OBJECT CREATION OF CUSTOMRESPONSE");
    }
    private HttpStatus status;
    private String errorMessage;
    private Object data;
}
