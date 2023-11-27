package com.traveljoy.member.service;

import com.traveljoy.member.dto.MemberJoinDto;
import com.traveljoy.member.entity.EmailVerificationCode;
import com.traveljoy.member.entity.Member;
import com.traveljoy.member.repository.EmailVerificationCodeRepository;
import com.traveljoy.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberServiceImpl implements MemberService {
    //레파지토리
    private final MemberRepository memberRepository;
    private final EmailVerificationCodeRepository emailVerificationCodeRepository;
    //맵퍼

    //메일
    private final JavaMailSender mailSender;
    //암호화
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Override
    @Transactional
    public void join(MemberJoinDto memberJoinDto) {
        //아이디중복검사
        boolean exists = checkDuplicateId(memberJoinDto.getMemberId());
        //이메일인증코드대조
        verificationCodeComparison(memberJoinDto.getEmail(), memberJoinDto.getVerificationCode());
        //회원가입
        Member member = Member.builder()
                .memberId(memberJoinDto.getMemberId())
                .memberPwd(passwordEncoder.encode(memberJoinDto.getMemberPwd()))
                .email(memberJoinDto.getEmail())
                .build();
        memberRepository.save(member);
    }

    public boolean checkDuplicateId(String memberId) {
        Optional<Member> existingMember = memberRepository.findByMemberId(memberId);
        return existingMember.isPresent();
    }

    public void verificationCodeComparison(String email, String verificationCode) {
        EmailVerificationCode emailVerificationCode = emailVerificationCodeRepository.findByEmail(email);

        if (emailVerificationCode == null || !emailVerificationCode.getCode().equals(verificationCode)) {
            throw new IllegalArgumentException("이메일 인증 코드가 일치하지 않습니다.");
        }

        if(emailVerificationCode.getExpiryDate().isBefore(LocalDateTime.now())) {
            emailVerificationCodeRepository.delete(emailVerificationCode);
            throw new IllegalArgumentException("인증 코드가 만료되었습니다.");
        }
    }
    //인증코드발송
    @Override
    @Async
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
    private static final String FROM_ADDRESS = "traveljoy241@gmail.com";

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
