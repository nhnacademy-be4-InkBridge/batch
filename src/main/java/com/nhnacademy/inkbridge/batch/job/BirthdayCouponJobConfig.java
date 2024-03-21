package com.nhnacademy.inkbridge.batch.job;

import com.nhnacademy.inkbridge.batch.entity.Coupon;
import com.nhnacademy.inkbridge.batch.entity.Member;
import com.nhnacademy.inkbridge.batch.entity.MemberCoupon;
import java.time.LocalDate;
import java.util.Collections;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
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
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * class: ExpireCouponJobConfig.
 *
 * @author JBum
 * @version 2024/03/19
 */
@Configuration
@EnableBatchProcessing
@EnableScheduling
@Slf4j
public class BirthdayCouponJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final JobLauncher jobLauncher;
    @PersistenceContext
    private final EntityManager entityManager;
    private static final int chunkSize = 10;

    public BirthdayCouponJobConfig(JobBuilderFactory jobBuilderFactory,
        StepBuilderFactory stepBuilderFactory, EntityManagerFactory entityManagerFactory,
        JobLauncher jobLauncher, EntityManager entityManager) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
        this.jobLauncher = jobLauncher;
        this.entityManager = entityManager;
    }

    @Bean
    public Job birthdayCouponJob() {
        return jobBuilderFactory.get("birthdayCouponJob")
            .incrementer(new RunIdIncrementer())
            .start(birthdayCreateStep())
            .next(birthdayCouponIssueStep())
            .build();
    }

    @Bean
    public Step birthdayCreateStep() {
        return stepBuilderFactory.get("birthdayCreateStep")
            .tasklet((contribution, chunkContext) -> {
                LocalDate now = LocalDate.now();
                Long count = entityManager.createQuery(
                        "SELECT COUNT(c) FROM Coupon c WHERE MONTH(c.basicIssuedDate) = :month AND YEAR(c.basicIssuedDate) = :year AND c.isBirth = true",
                        Long.class)
                    .setParameter("month", now.getMonthValue())
                    .setParameter("year", now.getYear())
                    .getSingleResult();
                log.info("생일쿠폰 갯수 : " + count);
                if (count > 0) {
                    log.info("Coupons for the current month already exist. Skipping creation.");
                    return RepeatStatus.FINISHED;
                } else {
                    // 중복된 쿠폰이 없을 경우에만 아래 쿠폰 생성 로직 실행
                    Coupon coupon = Coupon.createBirthdayCoupon(now);
                    log.info(coupon.toString());
                    entityManager.persist(coupon);
                    return RepeatStatus.FINISHED;
                }
            })
            .build();
    }
    @Bean
    public Step birthdayCouponIssueStep() {
        return stepBuilderFactory.get("birthdayCouponIssueStep")
            .<Member, MemberCoupon>chunk(chunkSize)
            .reader(memberLoad())
            .processor(issue())
            .writer(birthdayCouponWriter())
            .build();
    }
    @Bean
    public JpaPagingItemReader<Member> memberLoad() {
        return new JpaPagingItemReaderBuilder<Member>()
            .name("memberLoad")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(chunkSize)
            .queryString("SELECT m FROM Member m WHERE MONTH(m.birthday) = MONTH(:today)")
            .parameterValues(Collections.singletonMap("today", LocalDate.now()))
            .build();
    }
    @Bean
    public ItemProcessor<Member, MemberCoupon> issue() {
        return member -> {
                // 쿠폰 발급 로직 구현
            String couponId = entityManager.createQuery(
                    "SELECT c.couponId FROM Coupon c WHERE MONTH(c.basicIssuedDate) = :month AND YEAR(c.basicIssuedDate) = :year AND c.isBirth = true",
                    String.class)
                .setParameter("month", LocalDate.now().getMonthValue())
                .setParameter("year", LocalDate.now().getYear())
                .getSingleResult();
            // 이미 발급된 쿠폰인지 확인
            boolean couponAlreadyIssued = entityManager.createQuery(
                    "SELECT COUNT(mc) FROM MemberCoupon mc WHERE mc.memberId = :memberId AND mc.couponId = :couponId",
                    Long.class)
                .setParameter("memberId", member.getMemberId())
                .setParameter("couponId",couponId)
                .getSingleResult() > 0;

            if (couponAlreadyIssued) {
                // 이미 쿠폰을 발급받은 경우, null을 반환하여 해당 회원을 건너뜁니다.
                return null;
            }
            return MemberCoupon.issueMemberCoupon(couponId, member.getMemberId());
        };
    }
    @Bean
    public ItemWriter<MemberCoupon> birthdayCouponWriter() {
        return memberCoupons -> {
            // 쿠폰을 저장하는 로직 구현
            for (MemberCoupon memberCoupon : memberCoupons) {
                entityManager.persist(memberCoupon);
                log.info("MemberCoupon saved: {}", memberCoupon);
            }
        };
    }

    @Scheduled(cron = "0 0 0 1 * *")
    public void schedule()
        throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobRestartException, JobParametersInvalidException {
        JobParameters jobParameters = new JobParametersBuilder()
            .addLong("time", System.currentTimeMillis()) // 현재 시간을 이용하여 고유한 JobParameter 생성
            .toJobParameters();

        jobLauncher.run(birthdayCouponJob(), jobParameters);
    }
}