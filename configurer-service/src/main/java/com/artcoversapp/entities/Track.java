package com.artcoversapp.entities;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "tracks")
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToOne
    private CoverArt coverArt;
}
