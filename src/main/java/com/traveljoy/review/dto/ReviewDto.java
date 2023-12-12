package com.traveljoy.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDto {
    private Long memberId;
    private Long roomId;
    private String content;
    private Double rating;
}
