package com.example.batch10.config;

import com.example.batch10.domain.Member;
import com.example.batch10.domain.MemberStatus;
import com.example.batch10.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ChunkBatchJobConfig {
    private final JobRepository jobRepository;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final MemberRepository memberRepository;




    @Bean
    //JOB 생성
    public Job chuckJobConstruct() {

        log.info("********** This is chuckJobConstruct");
        return jobBuilderFactory.get("chuckJobConstruct")
                .preventRestart()
                .start(chuckStepConstruct())
                //.incrementer(new RunIdIncrementer())  //같은값 여러번호출시 job이 실행되지 않음
                .build();
    }

    @Bean
    @JobScope
    //Step 생성
    public Step chuckStepConstruct() {
        log.info("********** This is chuckStepConstruct");
        //log.info("********** This is requestDate : {}" ,requestDate );

        return stepBuilderFactory.get("chuckStepConstruct")
                .<Member, Member> chunk(20)
                .reader(chuckReader())
                .processor(chuckProcessor())
                .writer(chuckWriter())
                .build();
    }

    @Bean
    @StepScope
    //ItemReader
    public ListItemReader<Member> chuckReader() {
        log.info("********** This is ItemReader");
        //active인 맴버 조회
        List<Member> activeMembers = memberRepository.findByStatusEquals(MemberStatus.ACTIVE);
        List<Member> unPaidMembers = new ArrayList<>();
        for (Member member : activeMembers) {
            if(member.isUnpaid()) {
                unPaidMembers.add(member);
            }
        }
        return new ListItemReader<>(unPaidMembers);
    }

    //ItemProcessor
    public ItemProcessor<Member, Member> chuckProcessor() {
        return new ItemProcessor<Member, Member>() {
            @Override
            public Member process(Member member) throws Exception {
                log.info("********** ItemProcessor");
                return member.setStatusByUnPaid();
            }
        };
    }

    //ItemWriter
    public ItemWriter<Member> chuckWriter() {
        log.info("********** ItemWriter");

        return new ItemWriter<Member>() {
            @Override
            public void write(List<? extends Member> memberList) throws Exception {
                memberRepository.saveAll(memberList);
            }
        };


//        return ((List<? extends Member> memberList) ->
//                memberRepository.saveAll(memberList));
    }


}