package com.example.services.Assemblers;

import com.example.services.Controllers.TrackController;
import com.example.services.Models.CoverArt;
import com.example.services.Models.Track;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

//import org.graalvm.compiler.lir.CompositeValue;

@Component
public class CoverArtResourcesAssembler implements ResourceAssembler<CoverArt, Resource<CoverArt>> {
    @Override
    public Resource<CoverArt> toResource(CoverArt coverArt) {
        return new Resource<>(
                coverArt,
                linkTo(methodOn(TrackController.class).getTrack(coverArt.getId())).withSelfRel(),
                linkTo(methodOn(TrackController.class).getTrack()).withRel("coverArts")
        );
    }
}
