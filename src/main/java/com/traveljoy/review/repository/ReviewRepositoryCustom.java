package com.traveljoy.review.repository;

import com.traveljoy.review.dto.ReviewListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {

    Page<ReviewListDto> getReviewListByMemberId(Long memberId, Pageable pageable);
}
