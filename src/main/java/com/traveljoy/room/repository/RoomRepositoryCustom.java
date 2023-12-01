package com.traveljoy.room.repository;

import com.traveljoy.admin.dto.AdminRoomListDto;
import com.traveljoy.room.dto.RoomDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RoomRepositoryCustom {
    //관리자페이지 숙소 리스트
    Page<AdminRoomListDto> findRoomWithLocationAndThemeListByPage(Pageable pageable);
    //관리자페이지 숙소검색 리스트
    Page<AdminRoomListDto> findRoomWithLocationAndThemeListByPageAndSearch(Pageable pageable, String searchType, String keyword);
    //관리자페이지 숙소수정 숙소,숙소이미지리스트
    RoomDto getRoomAndImagesListById(Long id);
}
