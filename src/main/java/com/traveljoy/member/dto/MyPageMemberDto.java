package com.traveljoy.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyPageMemberDto {

    private String name;
    private String memberId;
    private String email;
    private String profileImage;
    private Long reviewCnt;

    public MyPageMemberDto(String name, String memberId, String email,String profileImage,Long reviewCnt) {
        this.name = name;
        this.memberId = memberId;
        this.email = email;
        this.profileImage = profileImage;
        this.reviewCnt = reviewCnt;
    }
}
