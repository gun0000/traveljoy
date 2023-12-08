package com.traveljoy.reservation.entity;

import com.traveljoy.common.baseentity.BaseTimeEntity;
import com.traveljoy.member.entity.Member;
import com.traveljoy.room.entity.Room;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class Reservation extends BaseTimeEntity {

    //연관관계
    //Member 회원 테이블
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //Room 숙소 테이블
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    //예약번호
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //예약자이름
    @Column(nullable = false)
    private String reserverName;

    //예약자이메일
    @Column(nullable = false)
    private String reserverEmail;

    //성인
    @Column(nullable = false)
    private Integer adult;

    //어린이
    @Column(nullable = false)
    private Integer child;

    //체크인
    @Column(nullable = false)
    private LocalDate checkIn;

    //체크아웃
    @Column(nullable = false)
    private LocalDate checkOut;

    //총결제금액
    @Column(nullable = false)
    private Long totalPayment;


    //결제상태
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PAYMENT_PENDING;

    public enum PaymentStatus {
        PAYMENT_COMPLETED ("결제완료"),
        PAYMENT_PENDING("결제대기중");

        @Getter
        private String status;

        PaymentStatus(String status) {
            this.status = status;
        }
    }




    @Builder
    public Reservation(Long id, String reserverName,String reserverEmail, Integer adult, Integer child, LocalDate checkIn,
                       LocalDate checkOut, Long totalPayment, PaymentStatus paymentStatus , Member member, Room room){
        this.id = id;
        this.reserverName = reserverName;
        this.reserverEmail = reserverEmail;
        this.adult = adult;
        this.child = child;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalPayment = totalPayment;
        this.paymentStatus = paymentStatus;
        this.member = member;
        this.room = room;
    }
}
