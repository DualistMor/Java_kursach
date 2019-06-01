package com.example.demo.Exceptions;

public class SingerNotFoundException extends RuntimeException {
    public SingerNotFoundException(Integer id){
        super("Could not find singer with ID: " + id);
    }
}
