package com.traveljoy.member.repository;

import com.traveljoy.member.entity.EmailVerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailVerificationCodeRepository extends JpaRepository<EmailVerificationCode, Long> {
    EmailVerificationCode findByEmail(String email);
}
