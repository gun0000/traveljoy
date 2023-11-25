package com.traveljoy.member.service;

import com.traveljoy.member.dto.EmailVerificationCodeDTO;

public interface MemberService {

    //인증코드보내기
    void sendVerificationCode(String email);
}
