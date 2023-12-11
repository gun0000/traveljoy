package com.traveljoy.member.repository;

import com.traveljoy.member.dto.MyPageReservationDto;

import java.util.List;

//QueryDSL
public interface MemberRepositoryCustom {
    //내정보페이지 예약내역
    List<MyPageReservationDto> getReservationShowBymemberId(Long memberId);
}
