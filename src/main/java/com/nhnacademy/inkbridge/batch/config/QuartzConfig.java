package com.nhnacademy.inkbridge.batch.config;

import com.nhnacademy.inkbridge.batch.job.BatchJobExecutor;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class QuartzConfig {

    public QuartzConfig(BatchJobExecutor batchJobExecutor) {
        this.batchJobExecutor = batchJobExecutor;
    }

    private final BatchJobExecutor batchJobExecutor;

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob()
            .ofType(BatchJobExecutor.class)
            .storeDurably()
            .withIdentity("batchJobExecutor")
            .withDescription("Invoke Spring Batch Job")
            .build();
    }

    @Bean
    public Trigger trigger(JobDetail job) {
        return TriggerBuilder.newTrigger()
            .forJob(job)
            .withIdentity("batchJobTrigger")
            .withDescription("Batch Job Trigger")
            .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(17, 51)) // 매일 1시에 실행
            .build();
    }
}