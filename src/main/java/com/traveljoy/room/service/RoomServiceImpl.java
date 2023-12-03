package com.traveljoy.room.service;

import com.traveljoy.admin.dto.AdminRoomListDto;
import com.traveljoy.room.dto.LocationDto;
import com.traveljoy.room.dto.RoomDto;
import com.traveljoy.room.dto.RoomShowDto;
import com.traveljoy.room.dto.ThemeDto;
import com.traveljoy.room.entity.Location;
import com.traveljoy.room.entity.Room;
import com.traveljoy.room.entity.RoomImage;
import com.traveljoy.room.entity.Theme;
import com.traveljoy.room.mapper.RoomMapper;
import com.traveljoy.room.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final LocationRepository locationRepository;
    private final ThemeRepository themeRepository;
    private final RoomImageRepository roomImageRepository;
    private final RoomMapper roomMapper;
    @Override
    public List<LocationDto> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return roomMapper.toLocationDtos(locations);
    }

    @Override
    public List<ThemeDto> getAllThemes() {
        List<Theme> themes = themeRepository.findAll();
        return roomMapper.toThemeDtos(themes);
    }

    @Override
    public void createRoom(RoomDto roomDto) {
        //숙소
        Location location = locationRepository.getReferenceById(roomDto.getLocationId());
        Theme theme = themeRepository.getReferenceById(roomDto.getThemeId());

        Room room = Room.builder()
                .name(roomDto.getName())
                .location(location)
                .theme(theme)
                .locationX(roomDto.getLocationX())
                .locationY(roomDto.getLocationY())
                .address(roomDto.getAddress())
                .description(roomDto.getDescription())
                .price(roomDto.getPrice())
                .build();
        roomRepository.save(room);
        //숙소이미지
        for (int i = 0; i < roomDto.getImages().size(); i++) {
            String imageUrl = roomDto.getImages().get(i);
            boolean isMain = false; // 기본적으로 isMain을 false로 설정합니다.
            if (i == 0) { // 첫 번째 이미지일 경우 isMain을 true로 설정합니다.
                isMain = true;
            }
            RoomImage roomImage = RoomImage.builder()
                    .room(room)
                    .image(imageUrl)
                    .isMain(isMain)
                    .build();
            roomImageRepository.save(roomImage);
        }

    }

    @Override
    public Page<AdminRoomListDto> findRoomWithLocationAndThemeListByPage(Pageable pageable) {
        return  roomRepository.findRoomWithLocationAndThemeListByPage(pageable);
    }
    @Override
    public Page<AdminRoomListDto> findRoomWithLocationAndThemeListByPageAndSearch(Pageable pageable, String searchType, String keyword) {
        return roomRepository.findRoomWithLocationAndThemeListByPageAndSearch(pageable, searchType, keyword);
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public RoomDto getRoomAndImagesListById(Long id) {
        return roomRepository.getRoomAndImagesListById(id);
    }
    @Override
    public void updateRoom(RoomDto roomDto) {
        // 숙소 정보 수정
        Room room = roomRepository.getReferenceById(roomDto.getId());
        Location location = locationRepository.getReferenceById(roomDto.getLocationId());
        Theme theme = themeRepository.getReferenceById(roomDto.getThemeId());

        room.update(roomDto.getName(), roomDto.getDescription(), roomDto.getPrice(), roomDto.getAddress(), roomDto.getLocationX(), roomDto.getLocationY(), theme, location);
        roomRepository.save(room);

        // 숙소 이미지 정보 수정
        if(!roomDto.getImages().isEmpty()) {
            for (int i = 0; i < roomDto.getImages().size(); i++) {
                try {
                String imageUrl = roomDto.getImages().get(i);
                Long imageId = roomDto.getImageId().get(i);

                RoomImage roomImage = roomImageRepository.getReferenceById(imageId);
                roomImage.update(imageUrl, i == 0); // 첫 번째 이미지일 경우 isMain을 true로 설정합니다.
                roomImageRepository.save(roomImage);
                } catch (Exception e) {
                    System.out.println("이미지 업데이트에 실패했습니다: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public List<RoomShowDto> getRoomShowByLocationId(Long id) {
        return roomRepository.getRoomShowByLocationId(id);
    }
    @Override
    public List<RoomShowDto> getRoomShowByThemeId(Long id) {
        return roomRepository.getRoomShowByThemeId(id);
    }


}
