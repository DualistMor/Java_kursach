package com.example.demo.Assemblers;

import com.example.demo.Controllers.DirectorController;
import com.example.demo.Models.Director;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class DirectorResourcesAssembler implements ResourceAssembler<Director, Resource<Director>> {
    @Override
    public Resource<Director> toResource(Director director) {
        return new Resource<>(
                director,
                linkTo(methodOn(DirectorController.class).getDirector(director.getId())).withSelfRel(),
                linkTo(methodOn(DirectorController.class).getDirectors()).withRel("directors")
        );
    }
}
