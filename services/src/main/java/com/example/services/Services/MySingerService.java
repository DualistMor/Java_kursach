package com.example.services.Services;

import com.example.services.Models.Singer;
import com.example.services.Repositories.SingerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MySingerService implements SingerService {
    @Autowired
    private SingerRepository singerRepository;

    @Override
    public List<Singer> getAll() {
        return singerRepository.findAll();
    }

    @Override
    public Optional<Singer> getObjectById(Integer integer) {
        return singerRepository.findById(integer);
    }

    @Override
    public Singer saveObject(Singer newObject) {
        return singerRepository.save(newObject);
    }

    @Override
    public void deleteObject(Integer integer) {
        singerRepository.deleteById(integer);
    }

    @Override
    public Singer updateObject(Singer newObject, Integer integer) {
        return singerRepository.findById(integer)
                .map(singer -> {
                    singer.setName(newObject.getName());
                    singer.setTracks(newObject.getTracks());
                    singer.setId(newObject.getId());
                    singer.setDeleted(newObject.isDeleted());
                    return singerRepository.save(singer);
                })
                .orElseGet(() -> {
                    newObject.setId(integer);
                    return singerRepository.save(newObject);
                });
    }
}
