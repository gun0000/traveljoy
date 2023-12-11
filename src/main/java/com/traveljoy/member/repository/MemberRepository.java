package com.traveljoy.member.repository;

import com.traveljoy.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> ,MemberRepositoryCustom{
    Optional<Member> findByMemberId(String memberId);
}
