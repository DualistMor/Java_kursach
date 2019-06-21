package com.artcoversapp.controllers;

import com.artcoversapp.entities.Singer;
import com.artcoversapp.repositories.SingerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/singers")
@RequiredArgsConstructor
public class SingerController {

    private final SingerRepository singerRepository;

    @GetMapping
    public List<Singer> getAll() {
        return singerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Singer getById(@PathVariable Integer id) {
        return findById(id);
    }

    @PostMapping
    public Singer create(@RequestBody Singer singer) {
        return singerRepository.save(singer);
    }

    @PutMapping
    public Singer update(@RequestBody Singer singer) {
        findById(singer.getId());
        return singerRepository.save(singer);
    }

    @DeleteMapping("/{id}")
    public Singer delete(@PathVariable Integer id) {
        Singer singer = findById(id);
        singerRepository.delete(singer);
        return singer;
    }

    private Singer findById(Integer id) {
        return singerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found entity by id"));
    }
}
