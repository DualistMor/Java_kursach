package com.example.services.Assemblers;

import com.example.services.Controllers.TrackController;
import com.example.services.Models.Track;
//import org.graalvm.compiler.lir.CompositeValue;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class TrackResourcesAssembler implements ResourceAssembler<Track, Resource<Track>> {
    @Override
    public Resource<Track> toResource(Track track) {
        return new Resource<>(
                track,
                linkTo(methodOn(TrackController.class).getTrack(track.getId())).withSelfRel(),
                linkTo(methodOn(TrackController.class).getTrack()).withRel("tracks")
        );
    }
}
