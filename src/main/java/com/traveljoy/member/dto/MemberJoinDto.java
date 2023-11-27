package com.traveljoy.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberJoinDto {

    private String memberId;
    private String memberPwd;
    private String email;
    private String verificationCode;

    @Builder
    public MemberJoinDto(String memberId, String memberPwd,String email) {
        this.memberId = memberId;
        this.memberPwd = memberPwd;
        this.email = email;
    }
}
