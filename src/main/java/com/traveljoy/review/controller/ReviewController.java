package com.traveljoy.review.controller;

import com.traveljoy.member.service.MemberPrincipal;
import com.traveljoy.review.dto.ReviewDto;
import com.traveljoy.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RequiredArgsConstructor
@Validated
@RequestMapping("/review")
@Controller
public class ReviewController {

    private final ReviewService reviewService;


    //숙소상세페이지 리뷰리스트

    //리뷰하기 페이지
    @GetMapping("/reg/{id}")
    public String reviewReg(@PathVariable Long id, Model model) {
        model.addAttribute("roomId",id);
        return "review/reviewReg";
    }
    //리뷰하기 폼
    //관리자 숙소 수정
    @PostMapping("/reg/{id}")
    public String saveReview(@PathVariable Long id, ReviewDto reviewDto, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberPrincipal MemberPrincipal = (MemberPrincipal) authentication.getPrincipal();
        Long memberId = MemberPrincipal.getId();
        reviewDto.setMemberId(memberId);
        reviewDto.setRoomId(id);
        try {
            reviewService.saveReview(reviewDto);
            redirectAttributes.addFlashAttribute("regSuccess", true);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("regSuccess", false);
        }
        return "redirect:/review/reg/"+id;
    }


    //리뷰삭제


}