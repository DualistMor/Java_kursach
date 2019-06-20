package com.example.services.Controllers;

import com.example.services.Assemblers.SingerResourcesAssembler;
import com.example.services.Assemblers.TrackResourcesAssembler;
import com.example.services.Exceptions.SingerNotExistTrackException;
import com.example.services.Exceptions.SingerNotFoundException;
import com.example.services.Exceptions.TrackNotFoundException;
import com.example.services.Models.CoverArt;
import com.example.services.Models.Singer;
import com.example.services.Models.Track;
import com.example.services.Services.CoverArtService;
import com.example.services.Services.SingerService;
import com.example.services.Services.TrackService;
import dto.SingerDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/coverArt")
public class CoverArtController {
    @Autowired
    CoverArtService coverArtService;

    @GetMapping
    public List<CoverArt> getAll() {
        return coverArtService.getAll();
    }

    @GetMapping("/{coverArtId}")
    public CoverArt getObjectById(@PathVariable Integer coverArtId) {
        return coverArtService.getObjectById(coverArtId).get();
    }

    @PostMapping
    public CoverArt saveObject(@RequestBody CoverArt newObject) {
        return coverArtService.saveObject(newObject);
    }

    @DeleteMapping("/{coverArtId}")
    public void deleteObject(@PathVariable Integer coverArtId) {
        coverArtService.deleteObject(coverArtId);
    }

    @PutMapping("/{coverArtId}")
    public CoverArt updateObject(@RequestBody CoverArt newObject, @PathVariable Integer coverArtId) {
        return coverArtService.updateObject(newObject, coverArtId);
    }
}
