package com.nhnacademy.inkbridge.batch.job;

import com.nhnacademy.inkbridge.batch.entity.Coupon;
import com.nhnacademy.inkbridge.batch.repository.CouponRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

/**
 * class: CouponExpirationTasklet.
 *
 * @author JBum
 * @version 2024/03/06
 */
@Slf4j
@Component
@StepScope
public class CouponExpirationTasklet implements Tasklet {

//    private final CouponRepository couponRepository;
//    public CouponExpirationTasklet(CouponRepository couponRepository) {
//        this.couponRepository = couponRepository;
//    }
//
//    @Override
//    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//        System.out.println("test");
//        LocalDate today = LocalDate.now();
//        List<Coupon> expiredCoupons = couponRepository.findByBasicExpiredDateBeforeAndCouponStatus_CouponStatusId(today,1);
//        log.info("test: {}",expiredCoupons.size());
//        // 만료된 쿠폰 처리 로직
//        for (Coupon coupon : expiredCoupons) {
//            log.info(coupon.toString());
//            coupon.expireCoupon();
//            couponRepository.save(coupon);
//        }
//        return RepeatStatus.FINISHED;
//    }
@Override
public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
    log.info("Start Step!!!");
    return RepeatStatus.FINISHED;
}
}