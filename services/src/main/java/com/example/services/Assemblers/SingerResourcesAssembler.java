package com.example.services.Assemblers;

import com.example.services.Controllers.SingerController;
import com.example.services.Models.Singer;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class SingerResourcesAssembler implements ResourceAssembler<Singer, Resource<Singer>> {
    @Override
    public Resource<Singer> toResource(Singer singer) {
        return new Resource<>(
                singer,
                linkTo(methodOn(SingerController.class).getSinger(singer.getId())).withSelfRel(),
                linkTo(methodOn(SingerController.class).getSingers()).withRel("singer")
        );
    }
}
