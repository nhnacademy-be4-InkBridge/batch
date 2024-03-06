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
 * class: CouponType.
 *
 * @author nhn
 * @version 2024/02/08
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "coupon_type")
public class CouponType {

    @Id
    @Column(name = "coupon_type_id")
    private Integer couponTypeId;

    @Column(name = "type_name")
    private String typeName;

    @Builder
    public CouponType(Integer couponTypeId,String typeName) {
        this.couponTypeId = couponTypeId;
        this.typeName=typeName;
    }
}
