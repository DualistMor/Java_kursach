package com.artcoversapp.controllers;

import com.artcoversapp.entities.Account;
import com.artcoversapp.services.AuthServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServices authServices;

    @PostMapping("/registration")
    public ResponseEntity<OAuth2AccessToken> register(@RequestBody @Valid Account account) {
        return new ResponseEntity<>(authServices.registration(account), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<OAuth2AccessToken> login(@RequestBody @Valid Account account) {
        return new ResponseEntity<>(authServices.login(account), HttpStatus.OK);
    }
}
