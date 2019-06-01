package com.example.services.Exceptions;

import com.example.services.Models.Track;
import com.example.services.Models.Singer;

public class SingerNotExistTrackException extends RuntimeException {
    public SingerNotExistTrackException(Singer singer, Track track) {
        super("Singer: " + singer.toString() + " does not shot track: " + track.toString());
    }

    public SingerNotExistTrackException(Integer singerId, Integer trackId) {
        super("Singer with id: " + singerId + " does not shot track with id: " + trackId);
    }
}
