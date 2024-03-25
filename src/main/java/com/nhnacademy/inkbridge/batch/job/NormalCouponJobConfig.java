package com.nhnacademy.inkbridge.batch.job;

import com.nhnacademy.inkbridge.batch.entity.Coupon;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * class: NormalCouponJobConfig.
 *
 * @author JBum
 * @version 2024/03/19
 */
@Configuration
@EnableBatchProcessing
@EnableScheduling
@Slf4j
public class NormalCouponJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final JobLauncher jobLauncher;
    private final EntityManager entityManager;
    private static final int chunkSize = 10;

    public NormalCouponJobConfig(JobBuilderFactory jobBuilderFactory,
        StepBuilderFactory stepBuilderFactory, EntityManagerFactory entityManagerFactory,
        JobLauncher jobLauncher, EntityManager entityManager) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
        this.jobLauncher = jobLauncher;
        this.entityManager = entityManager;
    }

    @Bean
    public Job normalCouponJob() {
        return jobBuilderFactory.get("normalCouponJob")
            .incrementer(new RunIdIncrementer())
            .start(normalStep())
            .build();
    }
    @Bean
    public Step normalStep() {
        return stepBuilderFactory.get("normalStep")
            .<Coupon, Coupon>chunk(chunkSize)
            .reader(normalLoad())
            .processor(normal())
            .writer(normalCouponWriter())
            .build();
    }
    @Bean
    public JpaPagingItemReader<Coupon> normalLoad() {
        return new JpaPagingItemReaderBuilder<Coupon>()
            .name("normalLoad")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(chunkSize)
            .queryString("SELECT c FROM Coupon c where c.basicIssuedDate < CURDATE() AND couponStatusId = 4")
            .build();
    }
    @Bean
    public ItemProcessor<Coupon, Coupon> normal() {
        return coupon -> {
            coupon.normal();
            return coupon;
        };
    }

    @Bean
    public ItemWriter<Coupon> normalCouponWriter() {
        return coupons -> {
            for (Coupon coupon : coupons) {
                log.info(coupon.toString());
                entityManager.merge(coupon);
            }
        };
    }
    @Scheduled(cron = "0 0 * * * *")
    public void schedule()
        throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        // 스프링 배치 작업 실행
        JobParameters jobParameters = new JobParametersBuilder()
            .addLong("time", System.currentTimeMillis())
            .toJobParameters();

        // 스프링 배치 작업 실행
        jobLauncher.run(normalCouponJob(), jobParameters);
    }
}