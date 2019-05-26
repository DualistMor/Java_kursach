package com.example.demo.Services;

import com.example.demo.Models.Director;
import com.example.demo.Repositories.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MyDirectorService implements DirectorService {
    @Autowired
    private DirectorRepository directorRepository;

    @Override
    public List<Director> getAll() {
        return directorRepository.findAll();
    }
    @Override
    public Optional<Director> getObjectById(Integer integer) {
        return directorRepository.findById(integer);
    }
    @Override
    public Director saveObject(Director newObject) {
        return directorRepository.save(newObject);
    }
    @Override
    public void deleteObject(Integer integer) {
        directorRepository.deleteById(integer);
    }
    @Override
    public Director updateObject(Director newObject, Integer integer) {
        return directorRepository.findById(integer)
                .map(zoo -> {
                    zoo.setName(newObject.getName());
                    zoo.setMovies(newObject.getMovies());
                    zoo.setId(newObject.getId());
                    return directorRepository.save(zoo);
                })
                .orElseGet(()->{
                    newObject.setId(integer);
                    return directorRepository.save(newObject);
                });
    }
}
