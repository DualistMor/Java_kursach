package com.artcoversapp.dto;

import lombok.Data;

@Data
public class CoverArt {
    private Integer id;
    private String url;
    private byte[] image;
}
