package com.example.services.Services;

import com.example.services.Models.Track;
import com.example.services.Repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MyTrackService implements TrackService {
    @Autowired
    private TrackRepository trackRepository;

    @Override
    public List<Track> getAll() {
        return trackRepository.findAll();
    }

    @Override
    public Optional<Track> getObjectById(Integer integer) {
        return trackRepository.findById(integer);
    }

    @Override
    public Track saveObject(Track newObject) {
        return trackRepository.save(newObject);
    }

    @Override
    public void deleteObject(Integer integer) {
        trackRepository.deleteById(integer);
    }

    @Override
    public Track updateObject(Track newObject, Integer integer) {
        return trackRepository.findById(integer)
                .map(track -> {
                    track.setCoverArt(newObject.getCoverArt());
                    track.setSinger(newObject.getSinger());
                    track.setName(newObject.getName());
                    track.setId(newObject.getId());
                    return trackRepository.save(track);
                })
                .orElseGet(() -> {
                    newObject.setId(integer);
                    return trackRepository.save(newObject);
                });
    }

}
