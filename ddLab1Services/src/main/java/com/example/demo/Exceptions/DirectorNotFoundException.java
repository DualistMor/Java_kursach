package com.example.demo.Exceptions;

public class DirectorNotFoundException extends RuntimeException {
    public DirectorNotFoundException(Integer id){
        super("Could not find director with ID: " + id);
    }
}
