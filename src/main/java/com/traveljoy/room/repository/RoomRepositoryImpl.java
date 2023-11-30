package com.traveljoy.room.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.traveljoy.admin.dto.AdminRoomListDto;
import com.traveljoy.room.entity.QLocation;
import com.traveljoy.room.entity.QRoom;
import com.traveljoy.room.entity.QTheme;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

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
}
