package com.traveljoy.member.controller;

import com.traveljoy.member.dto.CheckIdDto;
import com.traveljoy.member.dto.EmailVerificationCodeDto;
import com.traveljoy.member.dto.MemberJoinDto;
import com.traveljoy.member.dto.MyPageReservationDto;
import com.traveljoy.member.service.MemberPrincipal;
import com.traveljoy.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Validated
@RequestMapping("/member")
@Controller
public class MemberController {

    private final MemberService memberService;

    //회원가입 페이지
    @GetMapping("/join")
    public String join(Model model){
        model.addAttribute("MemberJoinDto", new MemberJoinDto());
        return "member/memberJoin";
    }
    //회원가입
    @PostMapping("/join")
    public String joinPost(@ModelAttribute("MemberJoinDto") MemberJoinDto memberJoinDto){
        memberService.join(memberJoinDto);
        return "redirect:/room/main";
    }
    //아이디중복검사
    @ResponseBody
    @PostMapping("/checkId")
    public CheckIdDto checkDuplicateId(@RequestBody CheckIdDto checkIdDto) {
        boolean result = memberService.checkDuplicateId(checkIdDto.getMemberId());
        checkIdDto.setResult(result);
        return checkIdDto;
    }
    //이메일인증 인증코드발송
    @PostMapping("/sendVerificationCode")
    public ResponseEntity<Void> sendVerificationCode(@RequestBody EmailVerificationCodeDto emailDTO) {
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
    @Secured({"ROLE_MEMBER","ROLE_ADMIN"})
    @GetMapping("/memberMyPage")
    public String memberMyPage(){
        return "member/memberMyPage";
    }
    //예약내역
    @Secured({"ROLE_MEMBER","ROLE_ADMIN"})
    @GetMapping("/memberMyPageReservationDetails")
    public String memberMyPageReservationDetails(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberPrincipal MemberPrincipal = (MemberPrincipal) authentication.getPrincipal();
        Long memberId = MemberPrincipal.getId();
        List<MyPageReservationDto> myPageReservationDtos = memberService.getReservationShowBymemberId(memberId);
        model.addAttribute("reservations", myPageReservationDtos);
        return "member/memberMyPageReservationDetails";
    }




}
