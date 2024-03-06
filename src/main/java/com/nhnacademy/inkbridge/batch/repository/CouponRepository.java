package com.nhnacademy.inkbridge.batch.repository;

import com.nhnacademy.inkbridge.batch.entity.Coupon;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: CouponRepository.
 *
 * @author JBum
 * @version 2024/02/15
 */
public interface CouponRepository extends JpaRepository<Coupon, String> {
    List<Coupon> findByBasicExpiredDateBeforeAndCouponStatus_CouponStatusId(LocalDate date, Integer statusId);
}
