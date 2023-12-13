package com.traveljoy.review.service;

import com.traveljoy.review.dto.ReviewDto;
import com.traveljoy.review.dto.ReviewListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ReviewService {
    void saveReview(ReviewDto reviewDto);

    Page<ReviewListDto> getReviewListByMemberId(Long memberId, Pageable pageable);

    void deleteReview(Long id);
}
