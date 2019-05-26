package com.example.demo.Assemblers;

import com.example.demo.Controllers.MovieController;
import com.example.demo.Models.Movie;
//import org.graalvm.compiler.lir.CompositeValue;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class MovieResourcesAssembler implements ResourceAssembler<Movie, Resource<Movie>> {
    @Override
    public Resource<Movie> toResource(Movie movie) {
        return new Resource<>(
                movie,
                linkTo(methodOn(MovieController.class).getMovie(movie.getId())).withSelfRel(),
                linkTo(methodOn(MovieController.class).getMovies()).withRel("movies")
        );
    }
}
