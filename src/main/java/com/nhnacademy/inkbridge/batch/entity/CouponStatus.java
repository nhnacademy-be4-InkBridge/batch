package com.nhnacademy.inkbridge.batch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: CouponStatus.
 *
 * @author JBum
 * @version 2024/02/23
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "coupon_status")
public class CouponStatus {

    @Id
    @Column(name = "coupon_status_id")
    private Integer couponStatusId;

    @Column(name = "coupon_status_name")
    private String couponStatusName;

    @Builder
    public CouponStatus(Integer couponStatusId, String couponStatusName) {
        this.couponStatusId = couponStatusId;
        this.couponStatusName = couponStatusName;
    }
}
