package com.traveljoy.reservation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ReservationDto {

    private Long roomId;
    private Long memberId;

    private Long id;
    private String name;
    private String email;
    private Integer adult;
    private Integer child;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Long totalPayment;

}
