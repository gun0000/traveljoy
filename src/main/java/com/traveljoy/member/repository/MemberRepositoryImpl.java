package com.traveljoy.member.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.traveljoy.member.dto.MyPageMemberDto;
import com.traveljoy.member.dto.MyPageReservationDto;
import com.traveljoy.member.entity.QMember;
import com.traveljoy.reservation.entity.QReservation;
import com.traveljoy.review.entity.QReview;
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
        QReview review = QReview.review;
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
                        reservation.room.id,
                        reservation.reserverName,
                        reservation.reserverEmail,
                        reservation.adult,
                        reservation.child,
                        reservation.checkIn,
                        reservation.checkOut,
                        reservation.totalPayment,
                        reservation.paymentStatus,
                        JPAExpressions
                                .selectOne()
                                .from(review)
                                .where(review.room.id.eq(reservation.room.id),
                                        review.member.id.eq(memberId))
                                .exists()
                        ))
                .from(reservation)
                .where(reservation.member.id.eq(memberId))
                .fetch();

        return myPageReservationDtos;
    }

    @Override
    public MyPageMemberDto getMyPageMember(Long memberId) {

        QMember member = QMember.member;
        QReview review = QReview.review;

        MyPageMemberDto myPageMemberDto = queryFactory
                .select(Projections.constructor(MyPageMemberDto.class,
                        member.name,
                        member.memberId,
                        member.email,
                        member.profileImage,
                        JPAExpressions
                                .select(review.count())
                                .from(review)
                                .where(review.member.id.eq(memberId))

                        ))
                .from(member)
                .where(member.id.eq(memberId))
                .fetchOne();

        return myPageMemberDto;
    }
}
