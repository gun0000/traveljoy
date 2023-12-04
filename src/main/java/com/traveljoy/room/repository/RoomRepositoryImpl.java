package com.traveljoy.room.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.traveljoy.admin.dto.AdminRoomListDto;
import com.traveljoy.review.entity.QReview;
import com.traveljoy.room.dto.RoomDto;
import com.traveljoy.room.dto.RoomShowDto;
import com.traveljoy.room.entity.QLocation;
import com.traveljoy.room.entity.QRoom;
import com.traveljoy.room.entity.QRoomImage;
import com.traveljoy.room.entity.QTheme;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    //관리자페이지 숙소 리스트
    @Override
    @Transactional(readOnly = true)
    public Page<AdminRoomListDto> findRoomWithLocationAndThemeListByPage(Pageable pageable) {
        QRoom room = QRoom.room;
        QLocation location = QLocation.location;
        QTheme theme = QTheme.theme;
        JPQLQuery<AdminRoomListDto> query = queryFactory
                .select(Projections.constructor(AdminRoomListDto.class, room.id, room.name, location.name, theme.name, room.price, room.createdAt))
                .from(room)
                .join(room.location, location)
                .join(room.theme, theme)
                .orderBy(room.id.asc());
        JPQLQuery<AdminRoomListDto> pageableQuery = query.offset(pageable.getOffset()).limit(pageable.getPageSize());
        List<AdminRoomListDto> content = pageableQuery.fetch();
        long total = pageableQuery.fetchCount();
        return new PageImpl<>(content, pageable, total);
    }
    //관리자페이지 검색 숙소 리스트
    @Override
    public Page<AdminRoomListDto> findRoomWithLocationAndThemeListByPageAndSearch(Pageable pageable, String searchType, String keyword) {
        QRoom room = QRoom.room;
        QLocation location = QLocation.location;
        QTheme theme = QTheme.theme;

        BooleanBuilder builder = new BooleanBuilder();

        if (searchType.equals("location")) {
            builder.and(location.name.contains(keyword));
        } else if (searchType.equals("theme")) {
            builder.and(theme.name.contains(keyword));
        }

        JPQLQuery<AdminRoomListDto> query = queryFactory
                .select(Projections.constructor(AdminRoomListDto.class, room.id, room.name, location.name, theme.name, room.price, room.createdAt))
                .from(room)
                .join(room.location, location)
                .join(room.theme, theme)
                .where(builder)
                .orderBy(room.id.asc());

        JPQLQuery<AdminRoomListDto> pageableQuery = query.offset(pageable.getOffset()).limit(pageable.getPageSize());
        List<AdminRoomListDto> content = pageableQuery.fetch();
        long total = pageableQuery.fetchCount();
        return new PageImpl<>(content, pageable, total);
    }

    public RoomDto getRoomAndImagesListById(Long id) {
        QRoom room = QRoom.room;
        QRoomImage roomImage = QRoomImage.roomImage;

        RoomDto roomDto = queryFactory.select(Projections.constructor(RoomDto.class,
                room.id,
                room.name,
                room.location.id,
                room.theme.id,
                room.locationX,
                room.locationY,
                room.address,
                room.description,
                room.price
                ))
                .from(room)
                .where(room.id.eq(id))
                .fetchOne();

        // 이미지 URL들을 가져오는 별도의 쿼리
        List<Tuple> imageTuples = queryFactory.select(roomImage.id,roomImage.image)
                .from(roomImage)
                .where(roomImage.room.id.eq(id))
                .fetch();
        List<Long> imageIds = new ArrayList<>();
        List<String> imageUrls = new ArrayList<>();
        for (Tuple imageTuple : imageTuples) {
            Long imageId = imageTuple.get(roomImage.id);
            String imageUrl = imageTuple.get(roomImage.image);
            imageIds.add(imageId);
            imageUrls.add(imageUrl);
        }
        roomDto.setImageId(imageIds);
        roomDto.setImages(imageUrls);  // RoomDto의 images 리스트에 이미지 URL들을 설정

        return roomDto;
    }

    @Override
    public List<RoomShowDto> getRoomShowByLocationId(Long id,int offset,int limit) {
        QRoom room = QRoom.room;
        QRoomImage roomImage = QRoomImage.roomImage;
        QReview review = QReview.review;

        List<RoomShowDto> roomShowDtos = queryFactory
                .select(Projections.constructor(
                        RoomShowDto.class,
                        roomImage.image,
                        JPAExpressions
                                .select(review.count())
                                .from(review)
                                .where(review.room.id.eq(room.id)),
                        room.id,
                        room.name,
                        JPAExpressions
                                .select(review.rating.avg().coalesce(0.0))
                                .from(review)
                                .where(review.room.id.eq(room.id)),
                        room.price
                ))
                .from(room)
                .join(room.Images, roomImage)
                .where(room.location.id.eq(id), roomImage.isMain.isTrue())
                .offset(offset)
                .limit(limit)
                .fetch();

        return roomShowDtos;
    }

    @Override
    public List<RoomShowDto> getRoomShowByThemeId(Long id,int offset,int limit) {
        QRoom room = QRoom.room;
        QRoomImage roomImage = QRoomImage.roomImage;
        QReview review = QReview.review;

        List<RoomShowDto> roomShowDtos = queryFactory
                .select(Projections.constructor(
                        RoomShowDto.class,
                        roomImage.image,
                        JPAExpressions
                                .select(review.count())
                                .from(review)
                                .where(review.room.id.eq(room.id)),
                        room.id,
                        room.name,
                        JPAExpressions
                                .select(review.rating.avg().coalesce(0.0))
                                .from(review)
                                .where(review.room.id.eq(room.id)),
                        room.price
                ))
                .from(room)
                .join(room.Images, roomImage)
                .where(room.theme.id.eq(id), roomImage.isMain.isTrue())
                .offset(offset)
                .limit(limit)
                .fetch();

        return roomShowDtos;
    }
    @Override
    public List<RoomShowDto> getPopularRooms(int offset,int limit) {
        String sql = "SELECT r.id, r.name, r.price, ri.image, " +
                "(SELECT COUNT(*) FROM review rv WHERE rv.room_id = r.id) AS reviewCount, " +
                "COALESCE((SELECT AVG(rv.rating) FROM review rv WHERE rv.room_id = r.id), 0) AS reviewAverage " +
                "FROM room r " +
                "JOIN room_image ri ON r.id = ri.room_id " +
                "WHERE ri.is_main = TRUE " +
                "ORDER BY reviewCount DESC, reviewAverage DESC " +
                "LIMIT " + limit + " " +
                "OFFSET "+offset;

        List<Object[]> result = entityManager.createNativeQuery(sql, Object[].class).getResultList();
        List<RoomShowDto> roomShowDtos = result.stream()
            .map(row -> {
                return new RoomShowDto(
                        (String) row[3],       // room image
                        ((Long) row[4]),  // review count
                        ((Long) row[0]),  // room id
                        (String) row[1],       // room name
                        ((BigDecimal) row[5]).doubleValue(), // review average
                        ((Long) row[2])); // room price
            })
            .collect(Collectors.toList());

        return roomShowDtos;
    }
    @Override
    public List<RoomShowDto> getRecentRoomsByids(List<Long> ids) {
        QRoom room = QRoom.room;
        QRoomImage roomImage = QRoomImage.roomImage;
        QReview review = QReview.review;

        List<RoomShowDto> roomShowDtos = queryFactory
                .select(Projections.constructor(
                        RoomShowDto.class,
                        roomImage.image,
                        JPAExpressions
                                .select(review.count())
                                .from(review)
                                .where(review.room.id.eq(room.id)),
                        room.id,
                        room.name,
                        JPAExpressions
                                .select(review.rating.avg().coalesce(0.0))
                                .from(review)
                                .where(review.room.id.eq(room.id)),
                        room.price
                ))
                .from(room)
                .join(room.Images, roomImage)
                .where(room.id.in(ids), roomImage.isMain.isTrue())
                .limit(16)
                .fetch();

        return roomShowDtos;
    }
}
