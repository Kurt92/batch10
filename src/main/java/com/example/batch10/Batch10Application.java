package com.example.batch10;

import com.example.framework.conf.QueryDslConf;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@EnableBatchProcessing
@SpringBootApplication
@Import(QueryDslConf.class)
public class Batch10Application {

    public static void main(String[] args) {
        SpringApplication.run(Batch10Application.class, args);
    }

}
