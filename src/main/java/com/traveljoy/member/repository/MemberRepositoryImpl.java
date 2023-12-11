package com.traveljoy.member.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.traveljoy.member.dto.MyPageReservationDto;
import com.traveljoy.reservation.entity.QReservation;
import com.traveljoy.room.entity.QRoom;
import com.traveljoy.room.entity.QRoomImage;
import lombok.RequiredArgsConstructor;

import java.util.List;

//QueryDSL
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MyPageReservationDto> getReservationShowBymemberId(Long memberId) {
        QReservation reservation = QReservation.reservation;
        QRoomImage roomImage = QRoomImage.roomImage;
        QRoom room = QRoom.room;
        List<MyPageReservationDto> myPageReservationDtos =queryFactory
                .select(Projections.constructor(
                        MyPageReservationDto.class,
                        JPAExpressions
                                .select(roomImage.image)
                                .from(roomImage)
                                .where(roomImage.isMain.isTrue(),
                                        roomImage.room.id.eq(reservation.room.id)),
                        JPAExpressions
                                .select(room.name)
                                .from(room)
                                .where(room.id.eq(reservation.room.id)),
                        reservation.id,
                        reservation.reserverName,
                        reservation.reserverEmail,
                        reservation.adult,
                        reservation.child,
                        reservation.checkIn,
                        reservation.checkOut,
                        reservation.totalPayment,
                        reservation.paymentStatus
                        ))
                .from(reservation)
                .where(reservation.id.eq(memberId))
                .fetch();

        return myPageReservationDtos;
    }
}
