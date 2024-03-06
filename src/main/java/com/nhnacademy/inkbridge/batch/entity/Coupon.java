package com.nhnacademy.inkbridge.batch.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: Coupon.
 *
 * @author nhn
 * @version 2024/02/08
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "coupon")
public class Coupon {

    @Id
    @Column(name = "coupon_id")
    private String couponId;

    @Column(name = "coupon_name")
    private String couponName;

    @Column(name = "min_price")
    private Long minPrice;

    @Column(name = "max_discount_price")
    private Long maxDiscountPrice;

    @Column(name = "discount_price")
    private Long discountPrice;

    @Column(name = "basic_issued_date")
    private LocalDate basicIssuedDate;

    @Column(name = "basic_expired_date")
    private LocalDate basicExpiredDate;

    @Column(name = "validity")
    private Integer validity;

    @Column(name = "is_birth")
    private Boolean isBirth;

    @ManyToOne
    @JoinColumn(name = "coupon_type_id")
    private CouponType couponType;

    @ManyToOne
    @JoinColumn(name = "coupon_status_id")
    private CouponStatus couponStatus;

    @Builder
    public Coupon(String couponId, String couponName, Long minPrice, Long maxDiscountPrice,
        Long discountPrice, LocalDate basicIssuedDate, LocalDate basicExpiredDate, Integer validity,
        Boolean isBirth, CouponType couponType, CouponStatus couponStatus) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.minPrice = minPrice;
        this.maxDiscountPrice = maxDiscountPrice;
        this.discountPrice = discountPrice;
        this.basicIssuedDate = basicIssuedDate;
        this.basicExpiredDate = basicExpiredDate;
        this.validity = validity;
        this.isBirth = isBirth;
        this.couponType = couponType;
        this.couponStatus = couponStatus;
    }

    public void expireCoupon(){
        this.couponStatus = CouponStatus.builder().couponStatusId(3).couponStatusName("EXPIRATION").build();
    }
}
