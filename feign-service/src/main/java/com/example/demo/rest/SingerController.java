package com.example.demo.rest;

import com.example.demo.clients.SingersServiceClient;
import dto.SingerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/singers")
public class SingerController {
    @Autowired
    SingersServiceClient singerServiceClient;

    @GetMapping("default/name")
    public ResponseEntity<String> getDefaultUsername() {
        return singerServiceClient.getDefaultUsername();
    }
}