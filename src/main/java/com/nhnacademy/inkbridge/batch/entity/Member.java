package com.nhnacademy.inkbridge.batch.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * class: member.
 *
 * @author minseo
 * @version 2/8/24
 */
@Entity
@Table(name = "member")
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "member_point")
    private Long memberPoint;

    @Column(name = "withdraw_at")
    private LocalDateTime withdrawAt;

    @Column(name = "member_auth_id")
    private Integer memberAuthId;

    @Column(name = "member_status_id")
    private Integer memberStatusId;

    @Column(name = "grade_id")
    private Integer memberGradeId;

    @Builder(builderMethodName = "create")
    public Member(Long memberId, String memberName, String phoneNumber, String email,
        LocalDate birthday, String password, LocalDateTime createdAt, Long memberPoint,
        LocalDateTime withdrawAt, Integer memberAuth, Integer memberStatus, Integer memberGrade) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthday = birthday;
        this.password = password;
        this.createdAt = createdAt;
        this.memberPoint = memberPoint;
        this.withdrawAt = withdrawAt;
        this.memberAuthId = memberAuth;
        this.memberStatusId = memberStatus;
        this.memberGradeId = memberGrade;
    }
}
