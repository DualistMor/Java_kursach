package com.example.services.Exceptions;

public class CoverArtNotFoundException extends RuntimeException {
    public CoverArtNotFoundException(Integer id) {
        super("Could not find CoverArt with ID: " + id);
    }

}
