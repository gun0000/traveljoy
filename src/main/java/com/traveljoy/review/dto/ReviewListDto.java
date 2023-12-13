package com.traveljoy.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewListDto {
    private Long id;
    private String content;
    private Double rating;
    private String roomName;
    private String roomImg;

    public ReviewListDto(Long id,String content,Double rating,String roomName,String roomImg){
        this.id = id;
        this.content = content;
        this.rating = rating;
        this.roomName = roomName;
        this.roomImg = roomImg;
    }
}
