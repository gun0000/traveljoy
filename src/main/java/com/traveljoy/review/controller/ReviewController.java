package com.traveljoy.review.controller;

import com.traveljoy.member.service.MemberPrincipal;
import com.traveljoy.review.dto.ReviewDto;
import com.traveljoy.review.dto.ReviewListDto;
import com.traveljoy.review.dto.ReviewListResponse;
import com.traveljoy.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@RequiredArgsConstructor
@Validated
@Secured({"ROLE_MEMBER","ROLE_ADMIN"})
@RequestMapping("/review")
@Controller
public class ReviewController {

    private final ReviewService reviewService;


    //회원페이지 내가 쓴 리뷰리스트 ajax
    @GetMapping("/list/{page}/{size}")
    @ResponseBody
    public ReviewListResponse getReviewListByMemberId(@PathVariable int page, @PathVariable int size){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberPrincipal MemberPrincipal = (MemberPrincipal) authentication.getPrincipal();
        Long memberId = MemberPrincipal.getId();

        Pageable pageable = PageRequest.of(page, size);

        Page<ReviewListDto> reviews = reviewService.getReviewListByMemberId(memberId,pageable);

        ReviewListResponse response = new ReviewListResponse();
        response.setReviews(reviews);
        return response;
    }
    //회원페이지 리뷰하기 페이지
    @GetMapping("/reg/{id}")
    public String reviewReg(@PathVariable Long id, Model model) {
        model.addAttribute("roomId",id);
        return "review/reviewReg";
    }
    //회원페이지 리뷰하기수정
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
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {

        try {
            reviewService.deleteReview(id);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}