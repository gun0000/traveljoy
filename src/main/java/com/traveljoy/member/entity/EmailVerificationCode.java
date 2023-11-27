package com.traveljoy.member.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class EmailVerificationCode {

    //이메일인증코드번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //이메일
    @Column(nullable = false)
    private String email;
    //인증코드
    @Column(nullable = false)
    private String code;
    //만료기간
    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @Builder
    public EmailVerificationCode(String code, String email, LocalDateTime expiryDate) {
        this.code = code;
        this.email = email;
        this.expiryDate = expiryDate;
    }
}
