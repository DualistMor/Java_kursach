package com.artcoversapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ConfigurerClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigurerClientApplication.class, args);
    }

}
