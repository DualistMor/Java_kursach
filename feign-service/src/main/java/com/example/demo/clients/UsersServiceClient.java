package com.example.demo.clients;
\
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@FeignClient(serviceId = "services")
public interface UsersServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "default/name")
    ResponseEntity<String> getDefaultUsername();

    @RequestMapping(method = RequestMethod.GET)
    Resources<Resource<Singer>> getSingers();

    @RequestMapping(method = RequestMethod.GET, value = "/{singerId}")
    ResponseEntity<ResourceSupport> getSinger(@PathVariable Integer singerId);

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> createSinger(@RequestBody Singer newSinger);

    @RequestMapping(method = RequestMethod.PUT, value = "/{singerId}")
    ResponseEntity<?> updateDirector(@RequestBody Singer updatedSinger, @PathVariable Integer singerId)

    @RequestMapping(method = RequestMethod.DELETE, value = "/{singerId}/delete")
    ResponseEntity<?> deleteSingerWithTrack(@PathVariable Integer singerId);

    @RequestMapping(method = RequestMethod.DELETE, value = "/{singerId}")
    ResponseEntity<?> deleteDirectorAndSaveMovies(@PathVariable Integer singerId);

    @RequestMapping(method = RequestMethod.GET, value = "/{singerId}/tracks")
    Resources<Resource<Track>> getTracksOfSinger(@PathVariable Integer singerId);
}
