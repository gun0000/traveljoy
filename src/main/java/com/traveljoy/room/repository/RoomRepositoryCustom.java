package com.traveljoy.room.repository;

import com.traveljoy.admin.dto.AdminRoomListDto;
import com.traveljoy.room.dto.RoomDetailDto;
import com.traveljoy.room.dto.RoomDto;
import com.traveljoy.room.dto.RoomShowDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface RoomRepositoryCustom {
    //관리자페이지 숙소 리스트
    Page<AdminRoomListDto> findRoomWithLocationAndThemeListByPage(Pageable pageable);
    //관리자페이지 숙소검색 리스트
    Page<AdminRoomListDto> findRoomWithLocationAndThemeListByPageAndSearch(Pageable pageable, String searchType, String keyword);
    //관리자페이지 숙소수정 숙소,숙소이미지리스트
    RoomDto getRoomAndImagesListById(Long id);

    //지역번호로 숙소간단리스트 가져오기
    List<RoomShowDto> getRoomShowByLocationId(Long id,int offset,int limit);
    //테마번호로 숙소간단리스트 가져오기
    List<RoomShowDto> getRoomShowByThemeId(Long id,int offset,int limit);
    //인기숙소
    List<RoomShowDto> getPopularRooms(int offset,int limit);
    //최근본 숙소
    List<RoomShowDto> getRecentRoomsByids(List<Long> ids);
    //숙소 검색
    List<RoomShowDto> getRoomShowBySearch(String search,int offset,int limit);
    //숙소 상세
    RoomDetailDto getRoomDetail(Long roomId);
}
