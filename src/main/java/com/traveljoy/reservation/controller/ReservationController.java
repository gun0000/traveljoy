package com.traveljoy.reservation.controller;

import com.traveljoy.member.service.MemberPrincipal;
import com.traveljoy.reservation.dto.ReservationDto;
import com.traveljoy.reservation.dto.ReservationShowDto;
import com.traveljoy.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

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
    @ResponseBody
    public ResponseEntity<Map<String, Boolean>> roomReservePay(@RequestBody ReservationDto reservationDto){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberPrincipal MemberPrincipal = (MemberPrincipal) authentication.getPrincipal();
        Long memberId = MemberPrincipal.getId();
        reservationDto.setMemberId(memberId);

        reservationService.saveReservation(reservationDto);

        Map<String, Boolean> response = new HashMap<>();
        response.put("success", true);

        return ResponseEntity.ok(response);
    }
}