package com.example.demo.Exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(Integer id){
        super("Could not find movie with ID: " + id);
    }

}
