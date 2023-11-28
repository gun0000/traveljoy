package com.traveljoy.member.entity;

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
public class Member extends BaseTimeEntity {

    //연관관계
    //Reservation 예약 테이블
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();
    //Review 리뷰 테이블
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private  List<Review> reviews = new ArrayList<>();

    //회원번호
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //이름
    @Column()
    private String name;
    //아이디
    @Column(nullable = false, unique = true)
    private String memberId;
    //비밀번호
    @Column(nullable = false)
    private String memberPwd;
    //이메일
    @Column(nullable = false)
    private String email;
    //회원상태
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    public enum MemberStatus {
        MEMBER_ACTIVE ("활동중인 계정"),
        MEMBER_WITHDRAW("탈퇴한 계정");

        @Getter
        private String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }
    //회원권한
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MemberRole memberRole = MemberRole.ROLE_MEMBER;

    public enum MemberRole {
        ROLE_MEMBER("ROLE_MEMBER"),
        ROLE_ADMIN("ROLE_ADMIN");

        @Getter
        private String role;

        MemberRole(String role) {
            this.role = role;
        }
    }
    //소셜로그인
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MemberLoginType memberLoginType = MemberLoginType.MEMBER_BASIC;

    public enum MemberLoginType {
        MEMBER_BASIC("기본"),
        MEMBER_SOCIAL_KAKAO("카카오"),
        MEMBER_SOCIAL_NAVER("네이버");

        @Getter
        private String loginType;

        MemberLoginType(String loginType) {
            this.loginType = loginType;
        }
    }
    //프로필이미지
    @Column(nullable = false)
    private String profileImage = "basicuser.svg";
    public void changeProfileImage(String newImage) {
        if(newImage == null || !newImage.matches(".*\\.(svg|jpg|png)$")) {
            throw new IllegalArgumentException("잘못된 이미지 확장자입니다.");
        }
        this.profileImage = newImage;
    }
    @Builder
    public Member(Long id, String name, String memberId, String memberPwd, String email) {
        this.id = id;
        this.name = name;
        this.memberId = memberId;
        this.memberPwd = memberPwd;
        this.email = email;
    }
}
