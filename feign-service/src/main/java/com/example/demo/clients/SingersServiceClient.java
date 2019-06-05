package com.example.demo.clients;

import dto.SingerDto;
import dto.TrackDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@FeignClient(serviceId = "services")
public interface SingersServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "default/name")
    ResponseEntity<String> getDefaultUsername();

//    @RequestMapping(method = RequestMethod.GET)
//    ResponseEntity<List<SingerDto>> getSingers();
//
//    @RequestMapping(method = RequestMethod.GET, value = "/{singerId}")
//    ResponseEntity<SingerDto> getSinger(@PathVariable("singerId") Integer singerId);
//
//    @RequestMapping(method = RequestMethod.POST)
//    ResponseEntity<?> createSinger(@RequestBody SingerDto newSinger);
//
//    @RequestMapping(method = RequestMethod.PUT, value = "/{singerId}")
//    ResponseEntity<?> updateDirector(@RequestBody SingerDto updatedSinger, @PathVariable("singerId") Integer singerId);
//
//    @RequestMapping(method = RequestMethod.DELETE, value = "/{singerId}/delete")
//    ResponseEntity<?> deleteSingerWithTrack(@PathVariable("singerId") Integer singerId);
//
//    @RequestMapping(method = RequestMethod.DELETE, value = "/{singerId}")
//    ResponseEntity<?> deleteDirectorAndSaveMovies(@PathVariable("singerId") Integer singerId);
//
//    @RequestMapping(method = RequestMethod.GET, value = "/{singerId}/tracks")
//    ResponseEntity<List<TrackDto>> getTracksOfSinger(@PathVariable("singerId") Integer singerId);
}
