package com.traveljoy.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {

    private Long id;
    private String name;
    private String memberId;
    private String email;
    private String memberStatus;
    private String memberRole;
    private String memberLoginType;
    private String profileImage;

    @Builder
    public MemberDto(Long id, String name, String memberId, String email, String memberStatus, String memberRole, String memberLoginType, String profileImage) {
        this.id = id;
        this.name = name;
        this.memberId = memberId;
        this.email = email;
        this.memberStatus = memberStatus;
        this.memberRole = memberRole;
        this.memberLoginType = memberLoginType;
        this.profileImage = profileImage;
    }
}
