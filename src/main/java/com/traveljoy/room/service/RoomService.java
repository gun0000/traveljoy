package com.traveljoy.room.service;

import com.traveljoy.admin.dto.AdminRoomListDto;
import com.traveljoy.room.dto.LocationDto;
import com.traveljoy.room.dto.RoomDto;
import com.traveljoy.room.dto.RoomShowDto;
import com.traveljoy.room.dto.ThemeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoomService {
    //지역 가져오기
    List<LocationDto> getAllLocations();

    //테마 가져오기
    List<ThemeDto> getAllThemes();

    //숙소 등록하기
    void createRoom(RoomDto roomDto);

    //관리자 숙소목록 리스트
    Page<AdminRoomListDto> findRoomWithLocationAndThemeListByPage(Pageable pageable);
    //관리자 숙소목록 검색리스트
    Page<AdminRoomListDto> findRoomWithLocationAndThemeListByPageAndSearch(Pageable pageable, String searchType, String keyword);
    //관리자 숙소 삭제
    void deleteRoom(Long id);
    //관리자 숙소 수정 페이지
    RoomDto getRoomAndImagesListById(Long id);

    void updateRoom(RoomDto roomDto);
    //지역번호로 숙소간단리스트 가져오기
    List<RoomShowDto> getRoomShowByLocationId(Long id);
    //테마번호로 숙소간단리스트 가져오기
    List<RoomShowDto> getRoomShowByThemeId(Long id);
    //인기숙소
    List<RoomShowDto> getPopularRooms();


}
