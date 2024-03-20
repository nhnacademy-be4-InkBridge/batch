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
import lombok.ToString;

/**
 * class: Coupon.
 *
 * @author JBum
 * @version 2024/03/20
 */
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
    public void expire(){
        this.couponStatusId = 3;
    }
}
