package com.traveljoy.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Validated
@RequestMapping("/reserve")
@Controller
public class ReservationController {
    //예약하기 페이지
    @GetMapping("/roomReserve")
    public String roomReserve(){
        return "room/roomReserve";
    }
    //결제하기
    @PostMapping("/{roomId}/payment")
    public String roomReservePay(){
        return "room/roomReserve";
    }
}