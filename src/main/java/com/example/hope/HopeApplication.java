package com.example.hope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HopeApplication {

    public static void main(String[] args) {
        SpringApplication.run(HopeApplication.class, args);
    }

}