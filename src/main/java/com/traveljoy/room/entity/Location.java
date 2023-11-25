package com.traveljoy.room.entity;

import com.traveljoy.common.baseentity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Location extends BaseTimeEntity {
    //연관관계
    //Room 숙소 테이블
    @OneToOne(mappedBy = "location")
    private Room room;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Builder
    public Location(Long id ,String name){
        this.id = id;
        this.name = name;
    }
}