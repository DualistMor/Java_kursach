package com.example.demo.Assemblers;

import com.example.demo.Controllers.TrackController;
import com.example.demo.Models.Track;
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
