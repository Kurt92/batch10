package com.example.batch10.contoller;

import com.example.batch10.config.ChunkBatchJobConfig;
import com.example.batch10.config.ChunkFileBatchJobConfig;
import com.example.batch10.config.TaskletBatchJobConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
public class BatchController {

    private final JobLauncher jobLauncher;
    private final ChunkBatchJobConfig chunkBatchJobConfig;
    private final ChunkFileBatchJobConfig chunkFileBatchJobConfig;

    private final TaskletBatchJobConfig taskletBatchJobConfig;


    @PutMapping("/batch10/chunk-sample")
    public void chunkTest() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        //Job 파라미터셋
        JobParameters jobParameters = new JobParametersBuilder()
                //.addString("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .addString("requestDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .toJobParameters();

        // Job 실행
        jobLauncher.run(chunkBatchJobConfig.chuckJobConstruct(), jobParameters);

    }

    @PutMapping("/batch10/chunk-file-sample")
    public void chunkFileTest() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        //Job 파라미터셋
        JobParameters jobParameters = new JobParametersBuilder()
                //.addString("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .addString("requestDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .toJobParameters();

        // Job 실행
        jobLauncher.run(chunkFileBatchJobConfig.chuckFileJobConstruct(), jobParameters);

    }


    @PutMapping("/batch10/tasklet-sample")
    public void taskletTest() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        //Job 파라미터셋
        JobParameters jobParameters = new JobParametersBuilder()
                //.addString("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .addString("requestDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .toJobParameters();

        jobLauncher.run(taskletBatchJobConfig.taskletJobConstruct() , jobParameters);


    }

}
