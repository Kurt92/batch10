package com.example.batch10.config;

import com.example.batch10.domain.Employees;
import com.example.batch10.domain.EmployessRepository;
import com.example.batch10.domain.Member;
import com.example.framework.querydsl.QuerydslPagingItemReader;
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
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.List;

import static com.example.batch10.domain.QEmployees.employees;
import static com.example.batch10.domain.QSalaries.salaries;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class EmployBatchJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private final EmployessRepository employessRepository;




    @Bean
    public Job employBatchJobConstruct() {
        return jobBuilderFactory.get("employBatchJob")
                .preventRestart()
                .start(empolyBatchStepConstruct())
                .build();
    }

    @Bean
    @JobScope
    public Step empolyBatchStepConstruct() {
        return stepBuilderFactory.get("employBatchStep")
                .<Employees, Employees>chunk(100)
                //.reader(employJoinReader(entityManagerFactory))     //JPQL
                .reader(employeesQuerydslReader())
                .processor(employeesProcessor())
                .writer(employeesWriter())
                .build();
    }

    //JpaPagingItemReader 를 통한 read (JPQL)
//    @Bean
//    public JpaPagingItemReader<Employees> employJoinReader(EntityManagerFactory entityManagerFactory) {
//        return new JpaPagingItemReaderBuilder<Employees>()
//                .name("employReader")
//                .entityManagerFactory(entityManagerFactory)
//                .queryString("select s from Salaries s inner join s.employee e on s.employee = e.empNo where s.salary > 140000")
//                .pageSize(30)
//                .build();
//    }

    @Bean
    public QuerydslPagingItemReader<Employees> employeesQuerydslReader() {

        return new QuerydslPagingItemReader<>(entityManagerFactory, 100, queryFactory -> queryFactory
                .selectFrom(employees)
                .innerJoin(salaries).on(employees.eq(salaries.employee))
                .where(
                        salaries.salary.intValue().gt(80000),
                        employees.deleteYn.eq("N")
                )
        );

    }



//    @Bean
//    @StepScope
//    public RepositoryItemReader<Employees> employJPArepoReader(EmployessRepository employessRepository) {
//        return new RepositoryItemReaderBuilder<Employees>()
//                .repository(employessRepository)
//                .methodName("")
//                .pageSize(30)
//                .sorts()
//                .arguments()
//                .build();
//    }



    public ItemProcessor<Employees, Employees> employeesProcessor() {
        return new ItemProcessor<Employees, Employees>() {
            @Override
            public Employees process(Employees employees) throws Exception {
                employees.setDeleteYn("Y");
                return employees;
            }
        };
    }

    @Bean
    public ItemWriter<Employees> employeesWriter () {

         return new ItemWriter<Employees>() {
             @Override
             public void write(List<? extends Employees> employeesList) throws Exception {
                 employessRepository.saveAll(employeesList);
             }
         };
    }



}
