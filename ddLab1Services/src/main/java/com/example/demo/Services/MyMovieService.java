package com.example.demo.Services;

import com.example.demo.Models.Movie;
import com.example.demo.Repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MyMovieService implements MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<Movie> getAll() {
        return movieRepository.findAll();
    }
    @Override
    public Optional<Movie> getObjectById(Integer integer) {
        return movieRepository.findById(integer);
    }
    @Override
    public Movie saveObject(Movie newObject) {
        return movieRepository.save(newObject);
    }
    @Override
    public void deleteObject(Integer integer) {
        movieRepository.deleteById(integer);
    }
    @Override
    public Movie updateObject(Movie newObject, Integer integer) {
        return movieRepository.findById(integer)
                .map(animal -> {
                    animal.setDirector(newObject.getDirector());
                    animal.setName(newObject.getName());
                    animal.setId(newObject.getId());
                    return movieRepository.save(animal);
                })
                .orElseGet(()->{
                    newObject.setId(integer);
                    return movieRepository.save(newObject);
                });
    }

}
