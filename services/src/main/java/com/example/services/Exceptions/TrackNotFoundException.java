package com.example.services.Exceptions;

public class TrackNotFoundException extends RuntimeException {
    public TrackNotFoundException(Integer id) {
        super("Could not find track with ID: " + id);
    }

}
