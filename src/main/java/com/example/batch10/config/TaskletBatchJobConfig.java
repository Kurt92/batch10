package com.example.batch10.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class TaskletBatchJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job taskletJobConstruct() {
        return jobBuilderFactory.get("taskletJobConstruct")
                .preventRestart()
                .start(taskletStepConstruct())
                .build();
    }


    @Bean
    @JobScope
    public Step taskletStepConstruct() {
        return stepBuilderFactory.get("taskletStepConstruct")
                .tasklet(taskletTest())
                .build();
    }

    private Tasklet taskletTest() {

        return (contribution, chunkContext) -> {
            List<String> items = getItems();

            log.info("items : "+ items.toString());

            return RepeatStatus.FINISHED;
        };
    }

    private List<String> getItems() {
        List<String> items = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            items.add(i + " test!");
        }
        System.out.println(items);
        return items;
    }

}