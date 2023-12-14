package com.traveljoy.member.service;

import com.traveljoy.member.dto.MemberJoinDto;
import com.traveljoy.member.dto.MyPageMemberDto;
import com.traveljoy.member.dto.MyPageReservationDto;

import java.util.List;

public interface MemberService {
    //회원가입
    void join(MemberJoinDto memberJoinDto);
    //인증코드보내기
    void sendVerificationCode(String email);
    //아이디 중복검사
    boolean checkDuplicateId(String memberId);
    //내정보페이지 예약내역
    List<MyPageReservationDto> getReservationShowBymemberId(Long memberId);
    //내정보페이지 회원정보
    MyPageMemberDto getMyPageMember(Long memberId);
}
