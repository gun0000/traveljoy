package com.traveljoy.reservation.service;

import com.traveljoy.reservation.dto.ReservationShowDto;


public interface ReservationService {

    //예약간단 가져오기
    ReservationShowDto getReservationShow(Long roomId,String memberId);
}
