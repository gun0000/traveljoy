package com.traveljoy.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
public class ReviewListResponse {
    private Page<ReviewListDto> reviews;
    private Long lastItemNumber;
}
