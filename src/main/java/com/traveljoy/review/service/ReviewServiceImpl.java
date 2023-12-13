package com.traveljoy.review.service;

import com.traveljoy.member.entity.Member;
import com.traveljoy.member.repository.MemberRepository;
import com.traveljoy.review.dto.ReviewDto;
import com.traveljoy.review.dto.ReviewListDto;
import com.traveljoy.review.entity.Review;
import com.traveljoy.review.repository.ReviewRepository;
import com.traveljoy.room.entity.Room;
import com.traveljoy.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;

    @Override
    public void saveReview(ReviewDto reviewDto) {
        Member member = memberRepository.getReferenceById(reviewDto.getMemberId());
        Room room = roomRepository.getReferenceById(reviewDto.getRoomId());
        Review review = Review.builder()
                .content(reviewDto.getContent())
                .rating(reviewDto.getRating())
                .member(member)
                .room(room)
                .build();
        reviewRepository.save(review);
    }

    @Override
    public Page<ReviewListDto> getReviewListByMemberId(Long memberId, Pageable pageable) {
        return reviewRepository.getReviewListByMemberId(memberId,pageable);
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
