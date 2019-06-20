package com.example.services.Services;

import com.example.services.Models.CoverArt;
import com.example.services.Models.Track;
import com.example.services.Repositories.CoverArtRepository;
import com.example.services.Repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MyCoverArtService implements CoverArtService {
    @Autowired
    private CoverArtRepository coverArtRepository;

    @Override
    public List<CoverArt> getAll() {
        return coverArtRepository.findAll();
    }

    @Override
    public Optional<CoverArt> getObjectById(Integer integer) {
        return coverArtRepository.findById(integer);
    }

    @Override
    public CoverArt saveObject(CoverArt newObject) {
        return coverArtRepository.save(newObject);
    }

    @Override
    public void deleteObject(Integer integer) {
        coverArtRepository.deleteById(integer);
    }

    @Override
    public CoverArt updateObject(CoverArt newObject, Integer integer) {
        return coverArtRepository.findById(integer)
                .map(coverArt -> {
                    coverArt.setCoverArt(newObject.getCoverArt());
                    coverArt.setId(newObject.getId());
                    coverArt.addTrack(newObject.getTrackId());
                    return coverArtRepository.save(coverArt);
                })
                .orElseGet(() -> {
                    newObject.setId(integer);
                    return coverArtRepository.save(newObject);
                });
    }
}
