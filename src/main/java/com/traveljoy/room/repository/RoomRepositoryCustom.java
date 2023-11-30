package com.traveljoy.room.repository;

import com.traveljoy.admin.dto.AdminRoomListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RoomRepositoryCustom {
    //관리자페이지 숙소 리스트
    public Page<AdminRoomListDto> findRoomWithLocationAndThemeListByPage(Pageable pageable);
}
