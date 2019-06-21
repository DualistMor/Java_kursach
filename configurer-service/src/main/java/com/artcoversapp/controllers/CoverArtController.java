package com.artcoversapp.controllers;

import com.artcoversapp.entities.CoverArt;
import com.artcoversapp.repositories.CoverArtRepository;
import com.artcoversapp.services.CoverArtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coverArts")
@RequiredArgsConstructor
public class CoverArtController {

    private final CoverArtRepository coverArtRepository;

    @Autowired
    CoverArtService coverArtService;

    @GetMapping
    public List<CoverArt> getAll() {
        return coverArtRepository.findAll();
    }

    @GetMapping("/{id}")
    public CoverArt getById(@PathVariable Integer id) {
        return findById(id);
    }

    @PostMapping
    public CoverArt create(@RequestBody String url) {
        return coverArtRepository.save(coverArtService.createCoverArt(url));
    }

    @PutMapping
    public CoverArt update(@RequestBody CoverArt coverArt) {
        findById(coverArt.getId());
        return coverArtRepository.save(coverArt);
    }

    @DeleteMapping("/{id}")
    public CoverArt delete(@PathVariable Integer id) {
        CoverArt coverArt = findById(id);
        coverArtRepository.delete(coverArt);
        return coverArt;
    }

    private CoverArt findById(Integer id) {
        return coverArtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found entity by id"));
    }
}
