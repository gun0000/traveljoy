package com.traveljoy.member.controller;

import com.traveljoy.member.dto.EmailVerificationCodeDTO;
import com.traveljoy.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Validated
@RequestMapping("/member")
@Controller
public class MemberController {

    private final MemberService memberService;

    //회원가입 페이지
    @GetMapping("/join")
    public String join(){
        return "member/memberJoin";
    }
    //이메일인증 인증코드발송
    @PostMapping("/sendVerificationCode")
    public ResponseEntity<Void> sendVerificationCode(@RequestBody EmailVerificationCodeDTO emailDTO) {
        System.out.printf(""+emailDTO);
        System.out.printf(""+emailDTO.getEmail());
        memberService.sendVerificationCode(emailDTO.getEmail());
        return ResponseEntity.ok().build();
    }
    //회원가입

    //로그인
    @GetMapping("/login")
    public String login(){
        return "member/memberLogin";
    }
    //로그아웃
    @PostMapping("/logout")
    public String logout(){
        return "room/roomMain";
    }
    //회원수정
    //회원탈퇴
    //문의하기


    //내 정보,리뷰 페이지
    @GetMapping("/memberMyPage")
    public String memberMyPage(){
        return "member/memberMyPage";
    }
    //예약내역
    @GetMapping("/memberMyPageReservationDetails")
    public String memberMyPageReservationDetails(){
        return "member/memberMyPageReservationDetails";
    }




}
