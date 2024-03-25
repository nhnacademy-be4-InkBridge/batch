package com.nhnacademy.inkbridge.batch.job;

import com.nhnacademy.inkbridge.batch.entity.BookOrderDetail;
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
 * class: OrderDoneJobConfig.
 *
 * @author JBum
 * @version 2024/03/25
 */
@Configuration
@EnableBatchProcessing
@EnableScheduling
@Slf4j
public class OrderDoneJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final JobLauncher jobLauncher;
    private final EntityManager entityManager;
    private static final int chunkSize = 10;

    public OrderDoneJobConfig(JobBuilderFactory jobBuilderFactory,
        StepBuilderFactory stepBuilderFactory, EntityManagerFactory entityManagerFactory,
        JobLauncher jobLauncher, EntityManager entityManager) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
        this.jobLauncher = jobLauncher;
        this.entityManager = entityManager;
    }

    @Bean
    public Job orderDoneJob() {
        return jobBuilderFactory.get("orderDoneJobConfig")
            .incrementer(new RunIdIncrementer())
            .start(orderDoneStep())
            .build();
    }
    @Bean
    public Step orderDoneStep() {
        return stepBuilderFactory.get("orderDoneStep")
            .<BookOrderDetail, BookOrderDetail>chunk(chunkSize)
            .reader(orderShippingLoad())
            .processor(done())
            .writer(orderDetailItemWriter())
            .build();
    }
    @Bean
    public JpaPagingItemReader<BookOrderDetail> orderShippingLoad() {
        return new JpaPagingItemReaderBuilder<BookOrderDetail>()
            .name("orderShippingLoad")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(chunkSize)
            .queryString("SELECT bd \n"
                + "FROM BookOrderDetail bd \n"
                + "LEFT JOIN BookOrder b ON b.orderId = bd.orderId \n"
                + "WHERE b.shipDate + 3 <= CURDATE() \n"
                + "AND bd.bookOrderStatusId = 2")
            .build();
    }
    @Bean
    public ItemProcessor<BookOrderDetail, BookOrderDetail> done() {
        return bookOrderDetail -> {
            bookOrderDetail.done();
            return bookOrderDetail;
        };
    }

    @Bean
    public ItemWriter<BookOrderDetail> orderDetailItemWriter() {
        return bookOrderDetails -> {
            for (BookOrderDetail bookOrderDetail : bookOrderDetails) {
                log.info(bookOrderDetail.toString());
                entityManager.merge(bookOrderDetail);
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
        jobLauncher.run(orderDoneJob(), jobParameters);
    }
}