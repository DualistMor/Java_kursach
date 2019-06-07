package com.example.demo.clients;

import dto.SingerDto;
import dto.TrackDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(serviceId = "services")
public interface SingersServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/singers/default/name")
    ResponseEntity<String> getDefaultUsername();

    @RequestMapping(method = RequestMethod.GET, value = "/singers")
    Resources<Resource<SingerDto>> getSingers();

    @RequestMapping(method = RequestMethod.GET, value = "/singers/{singerId}")
    ResponseEntity<SingerDto> getSinger(@PathVariable("singerId") Integer singerId);

    @RequestMapping(method = RequestMethod.POST, value = "/singers")
    ResponseEntity<?> createSinger(@RequestBody SingerDto newSinger);

    @RequestMapping(method = RequestMethod.PUT, value = "/singers/{singerId}")
    ResponseEntity<?> updateSinger(@RequestBody SingerDto updatedSinger, @PathVariable("singerId") Integer singerId);

    @RequestMapping(method = RequestMethod.DELETE, value = "/singers/{singerId}/delete")
    ResponseEntity<?> deleteSingerWithTracks(@PathVariable("singerId") Integer singerId);

    @RequestMapping(method = RequestMethod.DELETE, value = "/singers/{singerId}")
    ResponseEntity<?> deleteSingerAndSaveTracks(@PathVariable("singerId") Integer singerId);

    @RequestMapping(method = RequestMethod.GET, value = "/singers/{singerId}/tracks")
    Resources<Resource<TrackDto>> getTracksOfSinger(@PathVariable("singerId") Integer singerId);

    @RequestMapping(method = RequestMethod.DELETE, value = "/{singerId}/tracks/{trackId}")
    Resources<Resource<TrackDto>> deleteTrackFromSinger(@PathVariable("singerId") Integer singerId, @PathVariable("trackId") Integer trackId);
}
