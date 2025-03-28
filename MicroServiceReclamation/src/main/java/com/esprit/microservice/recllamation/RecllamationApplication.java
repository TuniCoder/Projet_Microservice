package com.esprit.microservice.recllamation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RecllamationApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecllamationApplication.class, args);
    }
    
}
