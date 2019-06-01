package com.example.services.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SingerNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(SingerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String singerNotFoundHandler(SingerNotFoundException ex) {
        return ex.getMessage();
    }
}
