package com.traveljoy.reservation.repository;

import com.traveljoy.reservation.dto.ReservationShowDto;

public interface ReservationRepositoryCustom {

    //예약간단 가져오기
    ReservationShowDto getReservationShow(Long roomId,String memberId);
}
