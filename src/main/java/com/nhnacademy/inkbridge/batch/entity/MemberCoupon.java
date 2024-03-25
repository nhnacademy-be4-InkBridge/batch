package com.nhnacademy.inkbridge.batch.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * class: memberCoupon.
 *
 * @author JBum
 * @version 2024/02/08
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "member_coupon")
@ToString
public class MemberCoupon {

    @Id
    @Column(name = "member_coupon_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberCouponId;

    @Column(name = "expired_at")
    private LocalDate expiredAt;

    @Column(name = "issued_at")
    private LocalDate issuedAt;

    @Column(name = "used_at")
    private LocalDate usedAt;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "coupon_id")
    private String couponId;

    @Builder
    public MemberCoupon(Long memberCouponId, LocalDate expiredAt, LocalDate issuedAt,
        LocalDate usedAt, Long memberId, String couponId) {
        this.memberCouponId = memberCouponId;
        this.expiredAt = expiredAt;
        this.issuedAt = issuedAt;
        this.usedAt = usedAt;
        this.memberId = memberId;
        this.couponId = couponId;
    }

    public static MemberCoupon issueMemberCoupon(String couponId, Long memberId){
        LocalDate now = LocalDate.now();
        return MemberCoupon.builder()
            .couponId(couponId)
            .memberId(memberId)
            .issuedAt(now)
            .expiredAt(now.withDayOfMonth(now.lengthOfMonth())).build();
    }
}
