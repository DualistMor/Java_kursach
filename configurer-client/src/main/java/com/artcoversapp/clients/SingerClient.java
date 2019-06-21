package com.artcoversapp.clients;

import com.artcoversapp.dto.Singer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "configurers-service", contextId = "singersClient", path = "/api/singers")
public interface SingerClient {
    @RequestMapping(method = RequestMethod.GET)
    List<Singer> getAll();

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    Singer getById(@PathVariable(value = "id") Integer id);

    @RequestMapping(method = RequestMethod.POST)
    Singer create(@RequestBody Singer singer);

    @RequestMapping(method = RequestMethod.PUT)
    Singer update(@RequestBody Singer singer);

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    Singer delete(@PathVariable(value = "id") Integer id);
}
