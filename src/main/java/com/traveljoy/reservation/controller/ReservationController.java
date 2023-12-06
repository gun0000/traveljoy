package com.traveljoy.reservation.controller;

import com.traveljoy.reservation.dto.ReservationShowDto;
import com.traveljoy.reservation.service.ReservationService;
import com.traveljoy.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Validated
@RequestMapping("/reserve")
@Controller
public class ReservationController {

    private final ReservationService reservationService;

    //예약하기 페이지
    @PostMapping("/roomReserve")
    public String roomReserve(@RequestParam("roomId") Long roomId,
                              @RequestParam(value = "checkIn", required = false) String checkIn,
                              @RequestParam(value = "checkOut", required = false) String checkOut,
                              @RequestParam(value = "total", required = false) Integer total,
                              @RequestParam(value = "adults", required = false) Integer adults,
                              @RequestParam(value = "children", required = false) Integer children,
                              Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String memberId = userDetails.getUsername();



        ReservationShowDto reservationShowDto = reservationService.getReservationShow(roomId,memberId);

        LocalDate checkInDate = LocalDate.parse(checkIn);
        LocalDate checkOutDate = LocalDate.parse(checkOut);
        long daysBetween = ChronoUnit.DAYS.between(checkInDate, checkOutDate);

        long pricePerNight = reservationShowDto.getPrice();  // 1박당 가격
        long totalPrice = (daysBetween * pricePerNight);  // 총 가격

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("reservation",reservationShowDto);
        model.addAttribute("roomId",roomId);
        model.addAttribute("checkIn",checkIn);
        model.addAttribute("checkOut",checkOut);
        model.addAttribute("total",total);
        model.addAttribute("adults",adults);
        model.addAttribute("children",children);
        return "room/roomReserve";
    }
    //결제하기
    @PostMapping("/{roomId}/payment")
    public String roomReservePay(){
        return "room/roomReserve";
    }
}