package com.nhnacademy.inkbridge.batch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * class: SimpleJobConfiguration.
 *
 * @author JBum
 * @version 2024/03/04
 */
@Slf4j // log 사용을 위한 lombok 어노테이션
@RequiredArgsConstructor // 생성자 DI를 위한 lombok 어노테이션
@Configuration
public class SimpleJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory; // 생성자 DI 받음
    private final StepBuilderFactory stepBuilderFactory; // 생성자 DI 받음
//
//    @Bean
//    public Job myFirstJob(JobRepository jobRepository){
//        return new JobBuilder("myFirstJob", jobRepository)
//            .start(myFirstStep(jobRepository))
//            .build();
//    }
//
//    @Bean
//    public Step myFirstStep(JobRepository jobRepository){
//        return new StepBuilder("myFirstStep",jobRepository)
//            .<String, String>chunk(1000,transactionManager)
//            .reader(itemReader())
//            .writer(itemWriter())
//            .build();
//    }
}