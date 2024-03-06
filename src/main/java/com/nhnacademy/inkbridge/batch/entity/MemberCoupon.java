//package com.nhnacademy.inkbridge.batch.entity;
//
//import java.time.LocalDate;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
///**
// * class: memberCoupon.
// *
// * @author nhn
// * @version 2024/02/08
// */
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Getter
//@Entity
//@Table(name = "member_coupon")
//public class MemberCoupon {
//
//    @Id
//    @Column(name = "member_coupon_id")
//    private String memberCouponId;
//
//    @Column(name = "expired_at")
//    private LocalDate expiredAt;
//
//    @Column(name = "issued_at")
//    private LocalDate issuedAt;
//
//    @Column(name = "used_at")
//    private LocalDate usedAt;
//
//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//    @ManyToOne
//    @JoinColumn(name = "coupon_id")
//    private Coupon coupon;
//
//    @Builder
//    public MemberCoupon(String memberCouponId, LocalDate expiredAt, LocalDate issuedAt,
//        LocalDate usedAt, Member member, Coupon coupon) {
//        this.memberCouponId = memberCouponId;
//        this.expiredAt = expiredAt;
//        this.issuedAt = issuedAt;
//        this.usedAt = usedAt;
//        this.member = member;
//        this.coupon = coupon;
//    }
//}
