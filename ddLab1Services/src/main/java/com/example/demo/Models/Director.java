package com.example.demo.Models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "directors")
@EntityListeners(AuditingEntityListener.class)
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "director_id")
    private int id;

    @NotNull
    private String name;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Movie> movies = new ArrayList<>();

    //public void addMovie(Movie movie){
        //movies.add(movie);
    //}

    public int getId() {
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Movie> getMovies(){
        return movies;
    }
    public void setMovies(List<Movie> movies){
        this.movies = movies;
    }
    public boolean removeMovie(Movie movie){
        return movies.contains(movie) && movies.remove(movie);

    }
    public boolean removeMovie(Integer id){
        for(int i = 0; i < movies.size(); ++i){
            if(movies.get(i).getId() == id)
                return movies.remove(movies.get(i));
        }
        return false;
    }

    public List<Movie> addMovie(Movie movie){
        if(movies.contains(movie))
            throw new RuntimeException("Movie already in this group!");
        movies.add(movie);
        return movies;
    }
}
