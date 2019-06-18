package com.example.services.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "singers")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class Singer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "singer_id")
    private int id;

    @NotNull
    private String name;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Track> tracks = new ArrayList<>();

    @NotNull
    @Column(columnDefinition = "Boolean default 'false'")
    private boolean isDeleted;

    //public void addTrack(Track movie){
    //tracks.add(movie);
    //}

    public boolean removeTrack(Track track) {
        return tracks.contains(track) && tracks.remove(track);

    }

    public boolean removeTrack(Integer id) {
        for (int i = 0; i < tracks.size(); ++i) {
            if (tracks.get(i).getId() == id)
                return tracks.remove(tracks.get(i));
        }
        return false;
    }

    public List<Track> addTrack(Track track) {
        if (tracks.contains(track))
            throw new RuntimeException("Track already in this group!");
        tracks.add(track);
        return tracks;
    }
}
