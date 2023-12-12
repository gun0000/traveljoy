package com.traveljoy.room.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RoomDetailDto {

    private List<String> image;
    private String locationName;
    private String themeName;
    private Long id;
    private String name;
    private String description;
    private String address;
    private String locationX;
    private String locationY;
    private Double rating;
    private Long price;
    private Long reviewCount;

    private List<RoomReviewDto> roomReviewDto;
    @Getter
    @Setter
    @NoArgsConstructor
    public static class RoomReviewDto{
        private String reviewMemberName;
        private String reviewContent;
        private Double reviewRating;
        private String reviewImage;
    }

}
