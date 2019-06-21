package com.artcoversapp.dto;

import lombok.Data;

@Data
public class Track {
    private Integer id;
    private String name;
    private CoverArt coverArt;
}
