package com.traveljoy.room.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomShowDto {
    private String image;
    private Long  reviewCount;
    private Long id;
    private String name;
    private Double rating;
    private Long price;
    private int offset = 1;
    private int limit = 16;

    public RoomShowDto(String image, Long reviewCount, Long id, String name, Double rating, Long price) {
        this.image = image;
        this.reviewCount = reviewCount;
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.price = price;
    }

}
