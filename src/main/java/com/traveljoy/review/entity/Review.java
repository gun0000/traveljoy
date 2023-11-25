package com.traveljoy.review.entity;

import com.traveljoy.common.baseentity.BaseTimeEntity;
import com.traveljoy.member.entity.Member;
import com.traveljoy.reservation.entity.Reservation;
import com.traveljoy.room.entity.Room;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Review extends BaseTimeEntity {
    //연관관계
    //Member 멤버 테이블
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //Room 숙소 테이블
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;
    //리뷰 번호
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //리뷰 내용
    @Column(nullable = false)
    private String content;

    //회원 평점
    @Column(nullable = false)
    private Double rating;

    //리뷰 이미지
    @Column
    private String reviewImage;

    @Builder
    public Review(Long id, String content, Double rating, String reviewImage, Member member, Room room){
        this.id = id;
        this.content = content;
        this.rating = rating;
        this.setImageUrl(reviewImage); // 검증을 통해 이미지 설정
        this.member = member;
        this.room = room;
    }

    public void setImageUrl(String reviewImage) {
        if(reviewImage == null || !reviewImage.matches(".*\\.(svg|jpg|png)$")) {
            throw new IllegalArgumentException("잘못된 이미지 확장자입니다.");
        }
        this.reviewImage = reviewImage;
    }
}
