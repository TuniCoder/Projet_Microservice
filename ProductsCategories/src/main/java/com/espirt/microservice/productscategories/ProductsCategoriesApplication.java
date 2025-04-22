package com.espirt.microservice.productscategories;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy
@CrossOrigin(origins = "http://localhost:4200")
public class ProductsCategoriesApplication {


    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Africa/Tunis"));
        SpringApplication.run(ProductsCategoriesApplication.class, args);
    }

}
