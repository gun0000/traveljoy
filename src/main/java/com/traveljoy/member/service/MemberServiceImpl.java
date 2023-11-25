package com.traveljoy.member.service;

import com.traveljoy.member.entity.EmailVerificationCode;
import com.traveljoy.member.repository.EmailVerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberServiceImpl implements MemberService {
    //레파지토리
    private final EmailVerificationCodeRepository emailVerificationCodeRepository;
    //맵퍼

    //메일
    private final JavaMailSender mailSender;


    private static final String FROM_ADDRESS = "traveljoy241@gmail.com";
    //인증코드발송
    @Override
    public void sendVerificationCode(String email) {
        String verificationCode = createVerificationCode();
        saveVerificationCode(email, verificationCode);
        sendEmail(email, verificationCode);
    }
    private String createVerificationCode() {
        //6자리의 랜덤 숫자를 인증 코드로 사용
        return String.valueOf((int)(Math.random() * 1000000));
    }
    private void saveVerificationCode(String email, String verificationCode) {
        // 현재 시간에서 1시간 뒤를 만료 시간으로 설정
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(1);

        EmailVerificationCode code = EmailVerificationCode.builder()
                .email(email)
                .code(verificationCode)
                .expiryDate(expiryDate)
                .build();

        emailVerificationCodeRepository.save(code);
    }
    private void sendEmail(String email, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        //받는사람
        message.setTo(email);
        //보내는사람
        message.setFrom(FROM_ADDRESS);
        //제목
        message.setSubject("트립조이 인증코드 발송");
        //내용
        message.setText("인증코드는" + verificationCode + "입니다.");

        mailSender.send(message);
    }
}
