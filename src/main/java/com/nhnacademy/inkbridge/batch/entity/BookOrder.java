package com.nhnacademy.inkbridge.batch.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: BookOrder.
 *
 * @author nhn
 * @version 2024/02/08
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "book_order")
@Builder
@AllArgsConstructor
public class BookOrder {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(name = "order_code")
    private String orderCode;


    @Column(name = "order_name")
    private String orderName;

    @Column(name = "ship_date")
    private LocalDate shipDate;

    @Column(name = "order_at")
    private LocalDateTime orderAt;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "receiver_number")
    private String receiverNumber;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "address")
    private String address;

    @Column(name = "address_detail")
    private String addressDetail;

    @Column(name = "orderer")
    private String orderer;

    @Column(name = "orderer_number")
    private String ordererNumber;

    @Column(name = "orderer_email")
    private String ordererEmail;

    @Column(name = "use_point")
    private Long usePoint;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "is_payment")
    private Boolean isPayment;

    @Column(name = "delivery_price")
    private Long deliveryPrice;


    @Column(name = "member_id")
    private Long memberId;

}
