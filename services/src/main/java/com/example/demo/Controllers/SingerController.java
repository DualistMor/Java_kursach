package com.example.demo.Controllers;

import com.example.demo.Assemblers.TrackResourcesAssembler;
import com.example.demo.Assemblers.SingerResourcesAssembler;
import com.example.demo.Exceptions.TrackNotFoundException;
import com.example.demo.Exceptions.SingerNotExistTrackException;
import com.example.demo.Exceptions.SingerNotFoundException;
import com.example.demo.Models.Singer;
import com.example.demo.Models.Track;
import com.example.demo.Services.TrackService;
import com.example.demo.Services.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/singers")
public class SingerController {
    @Autowired
    private SingerService singerService;
    @Autowired
    private TrackService trackService;

    private final SingerResourcesAssembler singerResourcesAssembler;
    private final TrackResourcesAssembler trackResourcesAssembler;

    @Value("${prop.user.defaultname}")
    private String defaultUsername;

    @GetMapping("default/name")
    public ResponseEntity<String> getDefaultUsername() {
        return ResponseEntity.ok(defaultUsername);
    }


    public SingerController(SingerResourcesAssembler singerResourcesAssembler, TrackResourcesAssembler trackResourcesAssembler){
        this.singerResourcesAssembler = singerResourcesAssembler;
        this.trackResourcesAssembler = trackResourcesAssembler;
    }

    @GetMapping
    public Resources<Resource<Singer>> getSingers(){
        List<Resource<Singer>> list = singerService.getAll().stream()
                .map(singerResourcesAssembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(
                list,
                linkTo(methodOn(SingerController.class).getSingers()).withSelfRel()
        );
    }
    @GetMapping("/{singerId}")
    public ResponseEntity<ResourceSupport> getSinger(@PathVariable Integer singerId){
        Optional<Singer> singer = singerService.getObjectById(singerId);
        return singer.isPresent() ?
                ResponseEntity
                        .ok(singerResourcesAssembler.toResource(singer.get())) :
                ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new VndErrors.VndError("Singer not found", new SingerNotFoundException(singerId).getMessage()));
    }
    @PostMapping
    public ResponseEntity<?> createSinger(@RequestBody Singer newSinger) throws URISyntaxException {
        Resource<Singer> resource = singerResourcesAssembler.toResource(singerService.saveObject(newSinger));
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }
    @PutMapping("/{singerId}")
    public ResponseEntity<?> updateDirector(@RequestBody Singer updatedSinger, @PathVariable Integer singerId) throws URISyntaxException {
        Singer updatedObj = singerService.getObjectById(singerId)
                .map(singer -> {
                    singer.setName(updatedSinger.getName());
                    singer.setTracks(updatedSinger.getTracks());
                    return singerService.saveObject(singer);
                })
                .orElseGet(()->{
                    updatedSinger.setId(singerId);
                    return singerService.saveObject(updatedSinger);
                });

        Resource<Singer> resource = singerResourcesAssembler.toResource(updatedObj);
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/{singerId}/delete")
    public ResponseEntity<?> deleteSingerWithTrack(@PathVariable Integer singerId){
        singerService.deleteObject(singerId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{singerId}")
    public ResponseEntity<?> deleteDirectorAndSaveMovies(@PathVariable Integer singerId){
        Singer singer = singerService.getObjectById(singerId)
                .orElseThrow(() -> new SingerNotFoundException(singerId));
        Track[] tracks = new Track[singer.getTracks().size()];
        tracks = singer.getTracks().toArray(tracks);
        for (Track stud:
                tracks) {
            Track track = trackService.getObjectById(stud.getId()).get();
            track.setSinger(null);
            singer.removeTrack(track);
            singer.setTracks(singer.getTracks());
            trackService.saveObject(track);
            singerService.saveObject(singer);
        }
        return deleteSingerWithTrack(singerId);
    }

    @GetMapping("/{singerId}/tracks")
    public Resources<Resource<Track>> getTracksOfSinger(@PathVariable Integer singerId){
        Optional<Singer> singer = singerService.getObjectById(singerId);
        List<Resource<Track>> list = singer.get().getTracks()
                .stream()
                .map(trackResourcesAssembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(
                list,
                linkTo(methodOn(SingerController.class).getSinger(singerId)).withSelfRel(),
                linkTo(methodOn(TrackController.class).getTrack()).withSelfRel()
        );
    }
    @DeleteMapping("/{singerId}/tracks/{trackId}")
    public Resources<Resource<Track>> removeTrackFromSinger(@PathVariable Integer singerId, @PathVariable Integer trackId){
        Track track = trackService.getObjectById(trackId)
                .orElseThrow(() -> new TrackNotFoundException(trackId));
        Singer singer = singerService.getObjectById(singerId)
                .orElseThrow(() -> new SingerNotFoundException(singerId));
        if(!singer.getTracks().contains(track))
            throw new SingerNotExistTrackException(singer, track);
        else {
            track.setSinger(null);
            singer.removeTrack(track);
            singer.setTracks(singer.getTracks());
            singerService.saveObject(singer);
            trackService.saveObject(track);
        }
        return getTracksOfSinger(singerId);
    }

}
