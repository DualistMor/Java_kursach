package com.example.demo.Exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;

public class TrackNotFoundException extends RuntimeException {
    public TrackNotFoundException(Integer id){
        super("Could not find track with ID: " + id);
    }

}
