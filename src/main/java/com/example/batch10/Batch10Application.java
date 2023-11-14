package com.example.batch10;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class Batch10Application {

    public static void main(String[] args) {
        SpringApplication.run(Batch10Application.class, args);
    }

}
