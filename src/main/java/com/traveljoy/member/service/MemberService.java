package com.traveljoy.member.service;

import com.traveljoy.member.dto.MemberJoinDto;

public interface MemberService {
    //회원가입
    void join(MemberJoinDto memberJoinDto);
    //인증코드보내기
    void sendVerificationCode(String email);
    //아이디 중복검사
    boolean checkDuplicateId(String memberId);
}
