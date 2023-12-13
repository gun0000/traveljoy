package com.traveljoy.review.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.traveljoy.review.dto.ReviewListDto;
import com.traveljoy.review.entity.QReview;
import com.traveljoy.room.entity.QRoomImage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewListDto> getReviewListByMemberId(Long memberId, Pageable pageable) {
        QReview review = QReview.review;
        QRoomImage roomImage = QRoomImage.roomImage;
        JPQLQuery<ReviewListDto> query = queryFactory
                .select(Projections.constructor(ReviewListDto.class,
                        review.id,
                        review.content,
                        review.rating,
                        review.room.name,
                        JPAExpressions
                                .select(roomImage.image)
                                .from(roomImage)
                                .where(roomImage.room.id.eq(review.room.id), roomImage.isMain.isTrue())
                ))
                .from(review)
                .where(review.member.id.eq(memberId))
                .orderBy(review.id.asc());
        JPQLQuery<ReviewListDto> pageableQuery = query.offset(pageable.getOffset()).limit(pageable.getPageSize());
        List<ReviewListDto> content = pageableQuery.fetch();
        long total = pageableQuery.fetchCount();
        return new PageImpl<>(content, pageable, total);
    }
}