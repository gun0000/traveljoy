package com.traveljoy.reservation.service;

import com.traveljoy.reservation.dto.ReservationShowDto;
import com.traveljoy.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    public ReservationShowDto getReservationShow(Long roomId ,String memberId) {
        return reservationRepository.getReservationShow(roomId,memberId);
    }
}
