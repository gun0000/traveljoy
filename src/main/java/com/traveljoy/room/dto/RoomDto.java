package com.traveljoy.room.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RoomDto {
    private Long id;
    private String name;
    private Long locationId;
    private Long themeId;
    private String locationX;
    private String locationY;
    private String address;
    private String description;
    private Long price;
    private List<String> images = new ArrayList<>();
}
