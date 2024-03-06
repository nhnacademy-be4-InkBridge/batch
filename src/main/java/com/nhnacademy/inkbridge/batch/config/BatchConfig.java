package com.nhnacademy.inkbridge.batch.config;

import com.nhnacademy.inkbridge.batch.job.CouponExpirationTasklet;
import com.nhnacademy.inkbridge.batch.job.LastTasklet;
import com.nhnacademy.inkbridge.batch.job.NextTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CouponExpirationTasklet tasklet;
    private final NextTasklet nextTasklet;
    private final LastTasklet lastTasklet;

    @Bean
    public Job couponExpiration() {
        System.out.println("test1");

        return jobBuilderFactory.get("couponExpiration")
            .start(startStep())
                .next(nextStep())
            .next(lastStep())

            .build();
    }


    @Bean
    public Step nextStep() {
        return stepBuilderFactory.get("nextStep")
            .tasklet(nextTasklet)
            .allowStartIfComplete(true) // 이 부분 추가
            .build();
    }

    @Bean
    public Step lastStep() {
        return stepBuilderFactory.get("lastStep")
            .tasklet(lastTasklet)
            .allowStartIfComplete(true) // 이 부분 추가
            .build();
    }
    @Bean
    public Step startStep() {
        System.out.println("test2");
        return stepBuilderFactory.get("startStep")
            .tasklet(tasklet)
            .allowStartIfComplete(true) // 이 부분 추가
            .build();
    }

}