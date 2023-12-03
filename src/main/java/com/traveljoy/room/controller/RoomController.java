package com.traveljoy.room.controller;

import com.traveljoy.room.dto.LocationDto;
import com.traveljoy.room.dto.RoomShowDto;
import com.traveljoy.room.dto.ThemeDto;
import com.traveljoy.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Validated
@RequestMapping("/room")
@Controller
public class RoomController {

    private final RoomService roomService;

//메인페이지 //상품리스트 여러개 보기
    @GetMapping("/main")
    public String roomMain(Model model){
        List<LocationDto> locations = roomService.getAllLocations();
        List<ThemeDto> themes = roomService.getAllThemes();
        model.addAttribute("locations", locations);
        model.addAttribute("themes", themes);
        return "room/roomMain";
    }
    //ajax 지역 숙소리스트
    @GetMapping("/main/{locationId}/location")
    @ResponseBody
    public List<RoomShowDto> roomMainLocationShows(@PathVariable Long locationId){
        return roomService.getRoomShowByLocationId(locationId);
    }
    //ajax 테마 숙소리스트
    @GetMapping("/main/{themeId}/theme")
    @ResponseBody
    public List<RoomShowDto> roomMainThemeShows(@PathVariable Long themeId){
        return roomService.getRoomShowByThemeId(themeId);
    }
    //ajax 인기 숙소리스트
    @GetMapping("/main/popular/")
    @ResponseBody
    public List<RoomShowDto> roomMainPopularRoomsShows(){
        return roomService.getPopularRooms();
    }
//숙소검색페이지 //상품리스트보기
    @GetMapping("/search")
    public String roomSearch(){
        return "room/roomSearch";
    }
//지역,테마,인기숙소 페이지 //상품리스트보기
    @GetMapping("/{type}")
    public String roomType(@PathVariable String type){

        switch (type) {
            //지역
            case "region":
                break;
            //테마
            case "theme":
                break;
            //인기숙소
            case "popular":
                break;
        }

        return "room/roomType";
    }
//숙소상세페이지 //상품보기
    @GetMapping("/detail")
    public String roomDetail(){
        return "room/roomDetail";
    }
}