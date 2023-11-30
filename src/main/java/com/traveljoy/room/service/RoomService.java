package com.traveljoy.room.service;

import com.traveljoy.admin.dto.AdminRoomListDto;
import com.traveljoy.room.dto.LocationDto;
import com.traveljoy.room.dto.RoomDto;
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
}
