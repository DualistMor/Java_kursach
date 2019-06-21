package com.artcoversapp.controllers;

import com.artcoversapp.clients.SingerClient;
import com.artcoversapp.dto.Singer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/singers")
@RequiredArgsConstructor
public class SingerController {

    private final SingerClient singerClient;

    @GetMapping
    public List<Singer> getAll() {
        return singerClient.getAll();
    }

    @GetMapping("/{id}")
    public Singer getById(@PathVariable Integer id) {
        return singerClient.getById(id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public Singer create(@RequestBody Singer singer) {
        return singerClient.create(singer);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping
    public Singer update(@RequestBody Singer singer) {
        return singerClient.update(singer);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public Singer delete(@PathVariable Integer id) {
        return singerClient.delete(id);
    }
}
