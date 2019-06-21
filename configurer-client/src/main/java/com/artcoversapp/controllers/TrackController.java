package com.artcoversapp.controllers;

import com.artcoversapp.clients.TrackClient;
import com.artcoversapp.dto.Track;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/tracks")
@RequiredArgsConstructor
public class TrackController {

    private final TrackClient trackClient;

    @GetMapping
    public List<Track> getAll() {
        return trackClient.getAll();
    }

    @GetMapping("/{id}")
    public Track getById(@PathVariable Integer id) {
        return trackClient.getById(id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public Track create(@RequestBody Track track) {
        return trackClient.create(track);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping
    public Track update(@RequestBody Track track) {
        return trackClient.update(track);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public Track delete(@PathVariable Integer id) {
        return trackClient.delete(id);
    }
}
