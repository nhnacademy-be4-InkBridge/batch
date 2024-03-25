package com.nhnacademy.inkbridge.batch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * class: BookOrderDetail.
 *
 * @author nhn
 * @version 2024/02/08
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "book_order_detail")
@ToString
public class BookOrderDetail {

    @Id
    @Column(name = "order_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;

    @Column(name = "book_price")
    private Long bookPrice;

    @Column(name = "wrapping_price")
    private Long wrappingPrice;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "order_status_id")
    private Long bookOrderStatusId;

    @Column(name = "wrappingId")
    private Long wrappingId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "member_coupon_id")
    private Long memberCouponId;

    @Builder
    public BookOrderDetail(Long orderDetailId, Long bookPrice, Long wrappingPrice, Integer amount,
        Long bookOrderStatusId, Long wrappingId, Long orderId, Long bookId,
        Long memberCouponId) {
        this.orderDetailId = orderDetailId;
        this.bookPrice = bookPrice;
        this.wrappingPrice = wrappingPrice;
        this.amount = amount;
        this.bookOrderStatusId = bookOrderStatusId;
        this.wrappingId = wrappingId;
        this.orderId = orderId;
        this.bookId = bookId;
        this.memberCouponId = memberCouponId;
    }

    public void done(){
        this.bookOrderStatusId=3L;
    }
}
