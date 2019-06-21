package com.artcoversapp.clients;

import com.artcoversapp.dto.CoverArt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "configurers-service", contextId = "coverArtsClient", path = "/api/coverArts")
public interface CoverArtClient {
    @RequestMapping(method = RequestMethod.GET)
    List<CoverArt> getAll();

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    CoverArt getById(@PathVariable(value = "id") Integer id);

    @RequestMapping(method = RequestMethod.POST)
    CoverArt create(@RequestBody String url);

    @RequestMapping(method = RequestMethod.PUT)
    CoverArt update(@RequestBody CoverArt coverArt);

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    CoverArt delete(@PathVariable(value = "id") Integer id);
}
