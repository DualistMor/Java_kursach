package com.artcoversapp.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cover_arts")
public class CoverArt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String url;
    private byte[] image;
}
