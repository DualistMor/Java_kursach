package com.artcoversapp.services;

import com.artcoversapp.entities.CoverArt;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
public class CoverArtService {

    private byte[] getByteArrayFromUrl(String stringUrl) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            byte[] chunk = new byte[4096];
            int bytesRead;
            InputStream stream = new URL(getUrl(stringUrl)).openStream();
            while ((bytesRead = stream.read(chunk)) > 0) {
                outputStream.write(chunk, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return outputStream.toByteArray();
    }

    private String getUrl(String requestUrl) {
        String[] arr = requestUrl.split("\"");
        for (String s : arr) {
            if(s.startsWith("http")) {
                return s;
            }
        }
        return null;
    }

    public CoverArt createCoverArt(String url) {
        CoverArt coverArt = new CoverArt();
        coverArt.setUrl(url);
        coverArt.setImage(getByteArrayFromUrl(url));
        return coverArt;
    }
}
