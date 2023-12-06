package com.traveljoy.reservation.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.traveljoy.member.entity.QMember;
import com.traveljoy.reservation.dto.ReservationShowDto;
import com.traveljoy.room.entity.QRoom;
import com.traveljoy.room.entity.QRoomImage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public ReservationShowDto getReservationShow(Long roomId ,String memberId) {
        QRoom room = QRoom.room;
        QRoomImage roomImage = QRoomImage.roomImage;
        QMember member = QMember.member;

        ReservationShowDto reservationShowDto = queryFactory
                .select(Projections.fields(
                        ReservationShowDto.class,
                        room.id,
                        room.name,
                        room.price,
                        roomImage.image,
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(member.email)
                                        .from(member)
                                        .where(member.memberId.eq(memberId)),
                                "email")
                ))
                .from(room)
                .join(room.Images, roomImage)
                .where(room.id.eq(roomId), roomImage.isMain.isTrue())
                .fetchFirst();

        return reservationShowDto;
    }
}
