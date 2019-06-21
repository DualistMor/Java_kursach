package com.artcoversapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ConfigurerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigurerServiceApplication.class, args);
    }

}
