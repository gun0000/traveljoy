package com.traveljoy.reservation.service;

import com.traveljoy.member.entity.Member;
import com.traveljoy.member.repository.MemberRepository;
import com.traveljoy.reservation.dto.ReservationDto;
import com.traveljoy.reservation.dto.ReservationShowDto;
import com.traveljoy.reservation.entity.Reservation;
import com.traveljoy.reservation.repository.ReservationRepository;
import com.traveljoy.room.entity.Room;
import com.traveljoy.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;


    @Override
    public ReservationShowDto getReservationShow(Long roomId ,String memberId) {
        return reservationRepository.getReservationShow(roomId,memberId);
    }

    @Override
    public void saveReservation(ReservationDto reservationDto) {

        Room room = roomRepository.getReferenceById(reservationDto.getRoomId());
        Member member = memberRepository.getReferenceById(reservationDto.getMemberId());

        Reservation reservation = Reservation.builder()
                .reserverName(reservationDto.getName())
                .reserverEmail(reservationDto.getEmail())
                .adult(reservationDto.getAdult())
                .child(reservationDto.getChild())
                .checkIn(reservationDto.getCheckIn())
                .checkOut(reservationDto.getCheckOut())
                .totalPayment(reservationDto.getTotalPayment())
                .paymentStatus(Reservation.PaymentStatus.PAYMENT_COMPLETED)
                .member(member)
                .room(room)
                .build();

        reservationRepository.save(reservation);
    }
}
