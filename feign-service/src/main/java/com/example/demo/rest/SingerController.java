package com.example.demo.rest;

import com.example.demo.clients.SingersServiceClient;
import dto.SingerDto;
import dto.TrackDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;

@RestController
@RequestMapping("/singers")
public class SingerController {
    @Autowired
    SingersServiceClient singerServiceClient;

    @GetMapping("default/name")
    public ResponseEntity<String> getDefaultUsername() {
        return singerServiceClient.getDefaultUsername();
    }

    @GetMapping
    public Resources<Resource<SingerDto>> getSingers() {
        return singerServiceClient.getSingers();
    }

    @GetMapping("/{singerId}")
    public ResponseEntity<SingerDto> getSinger(@PathVariable Integer singerId) {
        return singerServiceClient.getSinger(singerId);
    }

    @PostMapping
    public ResponseEntity<?> createSinger(@RequestBody SingerDto newSinger) {
        return singerServiceClient.createSinger(newSinger);
    }

    @PutMapping("/{singerId}")
    public ResponseEntity<?> updateSinger(@RequestBody SingerDto updatedSinger, @PathVariable Integer singerId) {
        return singerServiceClient.updateSinger(updatedSinger, singerId);
    }

    @DeleteMapping("/{singerId}/delete")
    public ResponseEntity<?> deleteSingerWithTrack(@PathVariable Integer singerId) {
        return singerServiceClient.deleteSingerWithTracks(singerId);
    }

    @DeleteMapping("/{singerId}")
    public ResponseEntity<?> deleteSingerAndSaveTracks(@PathVariable Integer singerId) {
        return singerServiceClient.deleteSingerAndSaveTracks(singerId);
    }

    @GetMapping("/{singerId}/tracks")
    public Resources<Resource<TrackDto>> getTracksOfSinger(@PathVariable Integer singerId) {
        return singerServiceClient.getTracksOfSinger(singerId);
    }

    @DeleteMapping("/{singerId}/tracks/{trackId}")
    public Resources<Resource<TrackDto>> deleteTrackFromSinger(@PathVariable Integer singerId, @PathVariable Integer trackId) {
        return singerServiceClient.deleteTrackFromSinger(singerId, trackId);
    }
}