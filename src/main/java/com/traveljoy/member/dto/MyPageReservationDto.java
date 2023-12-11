package com.traveljoy.member.dto;

import com.traveljoy.reservation.entity.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class MyPageReservationDto {
    private Long memberId;
    private String image;
    private String roomName;
    private Long id;
    private String reserverName;
    private String reserverEmail;
    private Integer adult;
    private Integer child;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Long totalPayment;
    private Reservation.PaymentStatus paymentStatus;

    public MyPageReservationDto(String image,String roomName,Long id,String reserverName,String reserverEmail
            ,Integer adult,Integer child,LocalDate checkIn,LocalDate checkOut,Long totalPayment,Reservation.PaymentStatus paymentStatus){
        this.image = image;
        this.roomName = roomName;
        this.id = id;
        this.reserverName = reserverName;
        this.reserverEmail = reserverEmail;
        this.adult = adult;
        this.child = child;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalPayment = totalPayment;
        this.paymentStatus = paymentStatus;
    }


}
