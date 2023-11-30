package com.traveljoy.admin.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AdminRoomListDto {
    private Long id;
    private String roomName;
    private String locationName;
    private String themeName;
    private Long price;
    private LocalDateTime createdAt;


    public AdminRoomListDto(Long id, String roomName, String locationName, String themeName, Long price, LocalDateTime createdAt) {
        this.id = id;
        this.roomName = roomName;
        this.locationName = locationName;
        this.themeName = themeName;
        this.price = price;
        this.createdAt = createdAt;
    }
}