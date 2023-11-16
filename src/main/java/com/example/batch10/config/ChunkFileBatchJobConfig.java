package com.example.batch10.config;

import com.example.batch10.domain.FileSaveTest;
import com.example.batch10.domain.FileSaveTestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ChunkFileBatchJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final FileSaveTestRepository fileSaveTestRepository;




    //Job 생성
    @Bean
    @JobScope
    public Job chuckFileJobConstruct() {
        return jobBuilderFactory.get("chuckJobConstruct")
                .preventRestart()
                .start(chuckFileStepConstruct())
                //.incrementer(new RunIdIncrementer())  //같은값 여러번호출시 job이 실행되지 않음
                .build();
    }


    //Step 생성
    @Bean
    @StepScope
    public Step chuckFileStepConstruct() {
        log.info("********** This is chuckStepConstruct");

        return stepBuilderFactory.get("chuckStepConstruct")
                .<FileSaveTest, FileSaveTest> chunk(10)
                .reader(chuckFileReader())
                .processor(chuckFileProcessor())
                .writer(chuckFileWriter())
                .build();
    }


    @Bean
    @StepScope
    //ItemReader
    public FlatFileItemReader<FileSaveTest> chuckFileReader() {

        FlatFileItemReader<FileSaveTest> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(System.getProperty("user.home")+"/Downloads/FileTest.csv"));
        reader.setEncoding("UTF-8");
        reader.setLinesToSkip(1); // 첫 번째 줄(헤더)을 건너뛰기
        reader.setLineMapper(new DefaultLineMapper<FileSaveTest>(){{
            setLineTokenizer(new DelimitedLineTokenizer(){{
                setDelimiter(",");
                setNames("name","age","email");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<FileSaveTest>(){{
                setTargetType(FileSaveTest.class);
            }});
        }});
        return reader;
    }

    public ItemProcessor<FileSaveTest, FileSaveTest> chuckFileProcessor() {
        return new ItemProcessor<FileSaveTest, FileSaveTest>() {
            @Override
            public FileSaveTest process(FileSaveTest fileSaveTest) throws Exception {
                log.info("********** ItemProcessor");
                return fileSaveTest;
            }
        };
    }

    public ItemWriter<FileSaveTest> chuckFileWriter() {
        return new ItemWriter<FileSaveTest>() {
            @Override
            public void write(List<? extends FileSaveTest> fileSaveTestList) throws Exception {
                log.info("********** ItemWriter");
                // 여기에서 데이터를 저장하는 로직을 구현하면 됩니다.
                // 예시로 데이터를 콘솔에 출력하는 것으로 대체하였습니다.
                for (FileSaveTest fileSaveTest : fileSaveTestList) {
                    System.out.println(fileSaveTest.toString());
                }
                fileSaveTestRepository.saveAll(fileSaveTestList);
            }
        };
    }


}
