package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ArtCoversServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtCoversServicesApplication.class, args);
    }

}
