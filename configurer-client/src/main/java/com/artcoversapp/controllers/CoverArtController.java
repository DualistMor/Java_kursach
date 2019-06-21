package com.artcoversapp.controllers;

import com.artcoversapp.clients.CoverArtClient;
import com.artcoversapp.dto.CoverArt;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coverArts")
@RequiredArgsConstructor
public class CoverArtController {

    private final CoverArtClient coverArtClient;

    @GetMapping
    public List<CoverArt> getAll() {
        return coverArtClient.getAll();
    }

    @GetMapping("/{id}")
    public CoverArt getById(@PathVariable Integer id) {
        return coverArtClient.getById(id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public CoverArt create(@RequestBody String url) {
        return coverArtClient.create(url);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping
    public CoverArt update(@RequestBody CoverArt coverArt) {
        return coverArtClient.update(coverArt);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public CoverArt delete(@PathVariable Integer id) {
        return coverArtClient.delete(id);
    }
}

