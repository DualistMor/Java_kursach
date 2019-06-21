package com.artcoversapp.controllers;

import com.artcoversapp.entities.Track;
import com.artcoversapp.repositories.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracks")
@RequiredArgsConstructor
public class TrackController {

    private final TrackRepository trackRepository;

    @GetMapping
    public List<Track> getAll() {
        return trackRepository.findAll();
    }

    @GetMapping("/{id}")
    public Track getById(@PathVariable Integer id) {
        return findById(id);
    }

    @PostMapping
    public Track create(@RequestBody Track track) {
        return trackRepository.save(track);
    }

    @PutMapping
    public Track update(@RequestBody Track track) {
        findById(track.getId());
        return trackRepository.save(track);
    }

    @DeleteMapping("/{id}")
    public Track delete(@PathVariable Integer id) {
        Track track = findById(id);
        trackRepository.delete(track);
        return track;
    }

    private Track findById(Integer id) {
        return trackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found entity by id"));
    }
}
