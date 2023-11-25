package com.traveljoy.room.entity;

import com.traveljoy.common.baseentity.BaseTimeEntity;
import com.traveljoy.reservation.entity.Reservation;
import com.traveljoy.review.entity.Review;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Room extends BaseTimeEntity {
    //연관관계
    //Location 지역테이블
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;
    //Theme 테마테이블
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id")
    private Theme theme;

    //Reservation 예약테이블
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();
    //Review 리뷰테이블
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();
    //Image 이미지테이블
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<RoomImage> Images = new ArrayList<>();

    //숙소번호
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //숙소이름
    @Column(nullable = false)
    private String name;

    //숙소내용
    @Column(nullable = false)
    private String description;

    //숙소가격
    @Column(nullable = false)
    private Long price;

    //숙소평점
    @Column(nullable = false)
    private Double rating = 0.0;

    //숙소주소
    @Column(nullable = false)
    private String address;

    //숙소X
    @Column(nullable = false)
    private Double locationX;

    //숙소Y
    @Column(nullable = false)
    private Double locationY;

    @Builder
    public Room(String name, String description, Long price, Double rating, String address, Double locationX, Double locationY, Theme theme, Location location) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.address = address;
        this.locationX = locationX;
        this.locationY = locationY;
        this.theme = theme;
        this.location = location;
    }
}
