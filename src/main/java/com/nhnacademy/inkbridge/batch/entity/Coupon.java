package com.nhnacademy.inkbridge.batch.entity;

import java.time.LocalDate;
import java.util.UUID;
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
import lombok.ToString;

/**
 * class: Coupon.
 *
 * @author JBum
 * @version 2024/03/20
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "coupon")
@ToString
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

    @Column(name = "coupon_type_id")
    private Integer couponTypeId;

    @Column(name = "coupon_status_id")
    private Integer couponStatusId;

    @Builder
    public Coupon(String couponId, String couponName, Long minPrice, Long maxDiscountPrice,
        Long discountPrice, LocalDate basicIssuedDate, LocalDate basicExpiredDate, Integer validity,
        Boolean isBirth, Integer couponTypeId, Integer couponStatusId) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.minPrice = minPrice;
        this.maxDiscountPrice = maxDiscountPrice;
        this.discountPrice = discountPrice;
        this.basicIssuedDate = basicIssuedDate;
        this.basicExpiredDate = basicExpiredDate;
        this.validity = validity;
        this.isBirth = isBirth;
        this.couponTypeId = couponTypeId;
        this.couponStatusId = couponStatusId;
    }

    public void expire(){
        this.couponStatusId = 3;
    }

    public static Coupon createBirthdayCoupon(LocalDate now) {
        return Coupon.builder()
            .couponId(UUID.randomUUID().toString())
            .couponName(now.getYear() + "년 " + now.getMonthValue() + "월 생일 쿠폰")
            .minPrice(0L)
            .maxDiscountPrice(10000L)
            .discountPrice(10000L)
            .basicIssuedDate(now.withDayOfMonth(1))
            .basicExpiredDate(now.withDayOfMonth(now.lengthOfMonth()))
            .validity(now.lengthOfMonth())
            .isBirth(true)
            .couponTypeId(2)
            .couponStatusId(1)
            .build();
    }

    public void normal() {
        this.couponStatusId=1;
    }
}
