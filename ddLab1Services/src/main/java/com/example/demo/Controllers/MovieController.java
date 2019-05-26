package com.example.demo.Controllers;

import com.example.demo.Assemblers.MovieResourcesAssembler;
import com.example.demo.Assemblers.DirectorResourcesAssembler;
import com.example.demo.Exceptions.DirectorNotFoundException;
import com.example.demo.Exceptions.MovieNotFoundException;
import com.example.demo.Models.Director;
import com.example.demo.Models.Movie;
import com.example.demo.Services.DirectorService;
import com.example.demo.Services.MovieService;
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
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @Autowired
    DirectorService directorService;
    private final MovieResourcesAssembler assembler;
    private final DirectorResourcesAssembler directorAssembler;

    public MovieController(MovieResourcesAssembler assembler, DirectorResourcesAssembler directorResourcesAssembler){
        this.assembler = assembler;
        this.directorAssembler = directorResourcesAssembler;
    }
    @GetMapping
    public Resources<Resource<Movie>> getMovies(){
        List<Resource<Movie>> list = movieService.getAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(
                list,
                linkTo(methodOn(MovieController.class).getMovies()).withSelfRel()
        );
    }
    @GetMapping("/{movieId}")
    public ResponseEntity<ResourceSupport> getMovie(@PathVariable Integer movieId){
        Optional<Movie> movie = movieService.getObjectById(movieId);
        return movie.isPresent() ?
                ok(assembler.toResource(movie.get())) :
                ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new VndErrors.VndError("Movie not found", new MovieNotFoundException(movieId).getMessage()));
    }
    @PostMapping
    public ResponseEntity<?> createMovie(@RequestBody Movie newMovie) throws URISyntaxException {
        Resource<Movie> resource = assembler.toResource(movieService.saveObject(newMovie));
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);    }
    @PutMapping("/{movieId}")
    public ResponseEntity<?> updateMovie(@RequestBody Movie updatedMovie, @PathVariable Integer movieId) throws URISyntaxException {
        Movie updatedObj = movieService.getObjectById(movieId)
                .map(movie -> {
                    movie.setName(updatedMovie.getName());
                    movie.setDirector(updatedMovie.getDirector());
                    return movieService.saveObject(movie);
                })
                .orElseGet(()->{
                    updatedMovie.setId(movieId);
                    return movieService.saveObject(updatedMovie);
                });

        Resource<Movie> resource = assembler.toResource(updatedObj);
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }
    @DeleteMapping("/{movieId}")
    public ResponseEntity<?> deleteMovie(@PathVariable Integer movieId){
        //try{
            //changeMovieDirector(movieId, -1);
            //movieService.deleteObject(movieId);
        //}
        //catch (Exception ex){
            //System.out.println(ex.getMessage());
        //}

        //return ResponseEntity.noContent().build();

        try{
            changeMovieDirector(movieId, -2);
            movieService.deleteObject(movieId);
        }
        catch(MovieNotFoundException ex){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new VndErrors.VndError("Movie not found", new MovieNotFoundException(movieId).getMessage()));
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{movieId}/director")
    public ResponseEntity<ResourceSupport> getZooOfAnimal(@PathVariable Integer movieId){
        Movie movie = movieService.getObjectById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(movieId));
        if(movie.getDirector() == null)
            return ResponseEntity.noContent().build();
        Optional<Director> director = directorService.getObjectById(movie.getDirector().getId());
        return director.isPresent() ?
                ok(directorAssembler.toResource(director.get())) :
                ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new VndErrors.VndError(
                                "Director not found. Remove this director movieId!!!",
                                new DirectorNotFoundException(movie.getDirector().getId()).getMessage())
                        );
    }

    @PostMapping("/{movieId}/director/{newDirectorId}")
    public Resources<Resource<Movie>> changeMovieDirector(@PathVariable Integer movieId, @PathVariable Integer newDirectorId){
        /* Movie movie = movieService.getObjectById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(movieId));
        if(movie.getZoo() != null
                && directorService.getObjectById(movie.getZoo().getId()).orElse(null) != null){
            if(movie.getZoo().getId() == newZooId){
                return getAnimalsOfZoo(newZooId);
            }
            Director zoo = directorService.getObjectById(movie.getZoo().getId()).get();
            zoo.removeAnimal(movie);
            zoo.setMovies(zoo.getMovies());
            directorService.saveObject(zoo);
        }
        Director newZoo = directorService.getObjectById(newZooId)
                .orElseThrow(() -> new DirectorNotFoundException(newZooId));
        movie.setZoo(newZoo);
        newZoo.setMovies(newZoo.addAnimal(movie));
        //movieService.saveObject(movie);
        //directorService.saveObject(newZoo);
        //return getAnimalsOfZoo(newZooId);
        */

        Movie movie = movieService.getObjectById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(movieId));
        removeMovieFromDirector(movie);
        return addMovieToDirector(movie, newDirectorId);
    }

    private Resources<Resource<Movie>> getMoviesOfDirector(Integer movieId){
        Optional<Director> director = directorService.getObjectById(movieId);
        List<Resource<Movie>> list = director.get().getMovies()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(
                list,
                linkTo(methodOn(DirectorController.class).getDirector(movieId)).withSelfRel(),
                linkTo(methodOn(MovieController.class).getMovies()).withSelfRel()
        );
    }

    private void removeMovieFromDirector(Movie movie){
        if(movie.getDirector() != null){
            Director animalDirector = directorService.getObjectById(movie.getDirector().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Delete this zoo ID"));
            animalDirector.removeMovie(movie);
            animalDirector.setMovies(animalDirector.getMovies());
            directorService.saveObject(animalDirector);
            movieService.saveObject(movie);
        }
    }

    private Resources<Resource<Movie>> addMovieToDirector(Movie movie, Integer directorId){
        Movie stud = movieService.getObjectById(movie.getId()).
                orElseThrow(() -> new IllegalArgumentException("Something wrong with movie: " + movie));
        if(directorId == -2)
            return null;
        Director director = directorService.getObjectById(directorId)
                .orElseThrow(() -> new DirectorNotFoundException(directorId));
        stud.setDirector(director);
        director.addMovie(stud);
        director.setMovies(director.getMovies());
        directorService.saveObject(director);
        movieService.saveObject(stud);
        return getMoviesOfDirector(directorId);
    }

}
