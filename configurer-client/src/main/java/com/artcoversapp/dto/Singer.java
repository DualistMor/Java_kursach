package com.artcoversapp.dto;

import lombok.Data;

import java.util.List;

@Data
public class Singer {
    private Integer id;
    private String name;
    private List<Track> tracks;
}
