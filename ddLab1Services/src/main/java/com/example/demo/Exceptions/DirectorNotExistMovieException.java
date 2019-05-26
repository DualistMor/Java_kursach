package com.example.demo.Exceptions;

import com.example.demo.Models.Movie;
import com.example.demo.Models.Director;

public class DirectorNotExistMovieException extends RuntimeException {
    public DirectorNotExistMovieException(Director director, Movie movie){
        super("Director: " + director.toString() + " does not shot movie: " + movie.toString());
    }
    public DirectorNotExistMovieException(Integer directorId, Integer movieId){
        super("Director with id: " + directorId + " does not shot movie with id: " + movieId);
    }
}
