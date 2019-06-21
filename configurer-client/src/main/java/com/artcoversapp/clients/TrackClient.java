package com.artcoversapp.clients;

import com.artcoversapp.dto.Track;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "configurers-service", contextId = "tracksClient", path = "/api/tracks")
public interface TrackClient {
    @RequestMapping(method = RequestMethod.GET)
    List<Track> getAll();

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    Track getById(@PathVariable(value = "id") Integer id);

    @RequestMapping(method = RequestMethod.POST)
    Track create(@RequestBody Track track);

    @RequestMapping(method = RequestMethod.PUT)
    Track update(@RequestBody Track track);

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    Track delete(@PathVariable(value = "id") Integer id);
}
