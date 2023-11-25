package com.traveljoy.room.entity;

import com.traveljoy.common.baseentity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class RoomImage extends BaseTimeEntity {
    //연관관계
    //Room 숙소테이블
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    //숙소이미지번호
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //이미지경로
    @Column(nullable = false)
    private String image;
    //대표이미지여부
    @Column(nullable = false)
    private boolean isMain;

    @Builder
    public RoomImage(Long id, Room room, String image, boolean isMain){
        this.id = id;
        this.room = room;
        this.image = image;
        this.isMain = isMain;
    }
}
