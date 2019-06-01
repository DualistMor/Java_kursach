package com.example.services.Controllers;

import com.example.services.Assemblers.TrackResourcesAssembler;
import com.example.services.Assemblers.SingerResourcesAssembler;
import com.example.services.Exceptions.SingerNotFoundException;
import com.example.services.Exceptions.TrackNotFoundException;
import com.example.services.Models.Singer;
import com.example.services.Models.Track;
import com.example.services.Services.SingerService;
import com.example.services.Services.TrackService;
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
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/tracks")
public class TrackController {
    @Autowired
    private TrackService trackService;
    @Autowired
    SingerService singerService;

    private final TrackResourcesAssembler trackResourcesAssembler;
    private final SingerResourcesAssembler singerResourcesAssembler;

    public TrackController(TrackResourcesAssembler trackResourcesAssembler, SingerResourcesAssembler singerResourcesAssembler) {
        this.trackResourcesAssembler = trackResourcesAssembler;
        this.singerResourcesAssembler = singerResourcesAssembler;
    }

    @GetMapping
    public Resources<Resource<Track>> getTrack() {
        List<Resource<Track>> list = trackService.getAll().stream()
                .map(trackResourcesAssembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(
                list,
                linkTo(methodOn(TrackController.class).getTrack()).withSelfRel()
        );
    }

    @GetMapping("/{trackId}")
    public ResponseEntity<ResourceSupport> getTrack(@PathVariable Integer trackId) {
        Optional<Track> track = trackService.getObjectById(trackId);
        return track.isPresent() ?
                ok(trackResourcesAssembler.toResource(track.get())) :
                ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new VndErrors.VndError("Track not found", new TrackNotFoundException(trackId).getMessage()));
    }

    @PostMapping
    public ResponseEntity<?> createTrack(@RequestBody Track newTrack) throws URISyntaxException {
        Resource<Track> resource = trackResourcesAssembler.toResource(trackService.saveObject(newTrack));
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @PutMapping("/{trackId}")
    public ResponseEntity<?> updateMovie(@RequestBody Track updatedTrack, @PathVariable Integer trackId) throws URISyntaxException {
        Track updatedObj = trackService.getObjectById(trackId)
                .map(track -> {
                    track.setName(updatedTrack.getName());
                    track.setSinger(updatedTrack.getSinger());
                    return trackService.saveObject(track);
                })
                .orElseGet(() -> {
                    updatedTrack.setId(trackId);
                    return trackService.saveObject(updatedTrack);
                });

        Resource<Track> resource = trackResourcesAssembler.toResource(updatedObj);
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/{trackId}")
    public ResponseEntity<?> deleteTrack(@PathVariable Integer trackId) {
        //try{
        //changeTrackSinger(movieId, -1);
        //trackService.deleteObject(movieId);
        //}
        //catch (Exception ex){
        //System.out.println(ex.getMessage());
        //}

        //return ResponseEntity.noContent().build();

        try {
            changeTrackSinger(trackId, -2);
            trackService.deleteObject(trackId);
        } catch (TrackNotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new VndErrors.VndError("Track not found", new TrackNotFoundException(trackId).getMessage()));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{trackId}/singer")
    public ResponseEntity<ResourceSupport> getSingerOfTrack(@PathVariable Integer trackId) {
        Track track = trackService.getObjectById(trackId)
                .orElseThrow(() -> new TrackNotFoundException(trackId));
        if (track.getSinger() == null)
            return ResponseEntity.noContent().build();
        Optional<Singer> singer = singerService.getObjectById(track.getSinger().getId());
        return singer.isPresent() ?
                ok(singerResourcesAssembler.toResource(singer.get())) :
                ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new VndErrors.VndError(
                                "Singer not found. Remove this singer trackId!!!",
                                new SingerNotFoundException(track.getSinger().getId()).getMessage())
                        );
    }

    @PostMapping("/{trackId}/singer/{newSingerId}")
    public Resources<Resource<Track>> changeTrackSinger(@PathVariable Integer trackId, @PathVariable Integer newSingerId) {
        /* Track track = trackService.getObjectById(movieId)
                .orElseThrow(() -> new TrackNotFoundException(movieId));
        if(track.getZoo() != null
                && singerService.getObjectById(track.getZoo().getId()).orElse(null) != null){
            if(track.getZoo().getId() == newZooId){
                return getAnimalsOfZoo(newZooId);
            }
            Singer zoo = singerService.getObjectById(track.getZoo().getId()).get();
            zoo.removeAnimal(track);
            zoo.setTracks(zoo.getTracks());
            singerService.saveObject(zoo);
        }
        Singer newZoo = singerService.getObjectById(newZooId)
                .orElseThrow(() -> new SingerNotFoundException(newZooId));
        track.setZoo(newZoo);
        newZoo.setTracks(newZoo.addAnimal(track));
        //trackService.saveObject(track);
        //singerService.saveObject(newZoo);
        //return getAnimalsOfZoo(newZooId);
        */

        Track track = trackService.getObjectById(trackId)
                .orElseThrow(() -> new TrackNotFoundException(trackId));
        removeTrackFromSinger(track);
        return addTrackToSinger(track, newSingerId);
    }

    private Resources<Resource<Track>> getTracksOfSinger(Integer trackId) {
        Optional<Singer> singer = singerService.getObjectById(trackId);
        List<Resource<Track>> list = singer.get().getTracks()
                .stream()
                .map(trackResourcesAssembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(
                list,
                linkTo(methodOn(SingerController.class).getSinger(trackId)).withSelfRel(),
                linkTo(methodOn(TrackController.class).getTrack()).withSelfRel()
        );
    }

    private void removeTrackFromSinger(Track track) {
        if (track.getSinger() != null) {
            Singer singer = singerService.getObjectById(track.getSinger().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Delete this singer ID"));
            singer.removeTrack(track);
            singer.setTracks(singer.getTracks());
            singerService.saveObject(singer);
            trackService.saveObject(track);
        }
    }

    private Resources<Resource<Track>> addTrackToSinger(Track track, Integer singerId) {
        Track trackToAdd = trackService.getObjectById(track.getId()).
                orElseThrow(() -> new IllegalArgumentException("Something wrong with track: " + track));
        if (singerId == -2)
            return null;
        Singer singer = singerService.getObjectById(singerId)
                .orElseThrow(() -> new SingerNotFoundException(singerId));
        trackToAdd.setSinger(singer);
        singer.addTrack(trackToAdd);
        singer.setTracks(singer.getTracks());
        singerService.saveObject(singer);
        trackService.saveObject(trackToAdd);
        return getTracksOfSinger(singerId);
    }

}
