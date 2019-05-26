package com.example.demo.Controllers;

import com.example.demo.Assemblers.MovieResourcesAssembler;
import com.example.demo.Assemblers.DirectorResourcesAssembler;
import com.example.demo.Exceptions.MovieNotFoundException;
import com.example.demo.Exceptions.DirectorNotExistMovieException;
import com.example.demo.Exceptions.DirectorNotFoundException;
import com.example.demo.Models.Director;
import com.example.demo.Models.Movie;
import com.example.demo.Services.MovieService;
import com.example.demo.Services.DirectorService;
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
@RequestMapping("/directors")
public class DirectorController {
    @Autowired
    private DirectorService directorService;
    @Autowired
    private MovieService movieService;

    private final DirectorResourcesAssembler assembler;
    private final MovieResourcesAssembler movieAssembler;

    @Value("${prop.user.defaultname}")
    private String defaultUsername;

    @GetMapping("default/name")
    public ResponseEntity<String> getDefaultUsername() {
        return ResponseEntity.ok(defaultUsername);
    }


    public DirectorController(DirectorResourcesAssembler assembler, MovieResourcesAssembler movieResourcesAssembler){
        this.assembler = assembler;
        this.movieAssembler = movieResourcesAssembler;
    }

    @GetMapping
    public Resources<Resource<Director>> getDirectors(){
        List<Resource<Director>> list = directorService.getAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(
                list,
                linkTo(methodOn(DirectorController.class).getDirectors()).withSelfRel()
        );
    }
    @GetMapping("/{directorId}")
    public ResponseEntity<ResourceSupport> getDirector(@PathVariable Integer directorId){
        Optional<Director> director = directorService.getObjectById(directorId);
        return director.isPresent() ?
                ResponseEntity
                        .ok(assembler.toResource(director.get())) :
                ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new VndErrors.VndError("Director not found", new DirectorNotFoundException(directorId).getMessage()));
    }
    @PostMapping
    public ResponseEntity<?> createDirector(@RequestBody Director newDirector) throws URISyntaxException {
        Resource<Director> resource = assembler.toResource(directorService.saveObject(newDirector));
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }
    @PutMapping("/{directorId}")
    public ResponseEntity<?> updateDirector(@RequestBody Director updatedDirector, @PathVariable Integer directorId) throws URISyntaxException {
        Director updatedObj = directorService.getObjectById(directorId)
                .map(director -> {
                    director.setName(updatedDirector.getName());
                    director.setMovies(updatedDirector.getMovies());
                    return directorService.saveObject(director);
                })
                .orElseGet(()->{
                    updatedDirector.setId(directorId);
                    return directorService.saveObject(updatedDirector);
                });

        Resource<Director> resource = assembler.toResource(updatedObj);
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/{directorId}/notsave")
    public ResponseEntity<?> deleteDirectorWithMovies(@PathVariable Integer directorId){
        directorService.deleteObject(directorId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{directorId}")
    public ResponseEntity<?> deleteDirectorAndSaveMovies(@PathVariable Integer directorId){
        Director director = directorService.getObjectById(directorId)
                .orElseThrow(() -> new DirectorNotFoundException(directorId));
        Movie[] movies = new Movie[director.getMovies().size()];
        movies = director.getMovies().toArray(movies);
        for (Movie stud:
                movies) {
            Movie movie = movieService.getObjectById(stud.getId()).get();
            movie.setDirector(null);
            director.removeMovie(movie);
            director.setMovies(director.getMovies());
            movieService.saveObject(movie);
            directorService.saveObject(director);
        }
        return deleteDirectorWithMovies(directorId);
    }

    @GetMapping("/{directorId}/movies")
    public Resources<Resource<Movie>> getMoviesOfDirector(@PathVariable Integer directorId){
        Optional<Director> director = directorService.getObjectById(directorId);
        List<Resource<Movie>> list = director.get().getMovies()
                .stream()
                .map(movieAssembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(
                list,
                linkTo(methodOn(DirectorController.class).getDirector(directorId)).withSelfRel(),
                linkTo(methodOn(MovieController.class).getMovies()).withSelfRel()
        );
    }
    @DeleteMapping("/{directorId}/movies/{movieId}")
    public Resources<Resource<Movie>> removeMovieFromDirector(@PathVariable Integer directorId, @PathVariable Integer animalId){
        Movie movie = movieService.getObjectById(animalId)
                .orElseThrow(() -> new MovieNotFoundException(animalId));
        Director director = directorService.getObjectById(directorId)
                .orElseThrow(() -> new DirectorNotFoundException(directorId));
        if(!director.getMovies().contains(movie))
            throw new DirectorNotExistMovieException(director, movie);
        else {
            movie.setDirector(null);
            director.removeMovie(movie);
            director.setMovies(director.getMovies());
            directorService.saveObject(director);
            movieService.saveObject(movie);
        }
        return getMoviesOfDirector(directorId);
    }

}
