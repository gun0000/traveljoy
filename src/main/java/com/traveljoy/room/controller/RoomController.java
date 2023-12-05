package com.traveljoy.room.controller;

import com.traveljoy.room.dto.LocationDto;
import com.traveljoy.room.dto.RoomDetailDto;
import com.traveljoy.room.dto.RoomShowDto;
import com.traveljoy.room.dto.ThemeDto;
import com.traveljoy.room.service.RoomService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
    @GetMapping("/main/{locationId}/location/{offset}/{limit}")
    @ResponseBody
    public List<RoomShowDto> roomMainLocationShows(@PathVariable Long locationId, @PathVariable int offset,@PathVariable int limit){
        return roomService.getRoomShowByLocationId(locationId, offset, limit);
    }
    //ajax 테마 숙소리스트
    @GetMapping("/main/{themeId}/theme/{offset}/{limit}")
    @ResponseBody
    public List<RoomShowDto> roomMainThemeShows(@PathVariable Long themeId, @PathVariable int offset,@PathVariable int limit){
        return roomService.getRoomShowByThemeId(themeId, offset, limit);
    }
    //ajax 인기 숙소리스트
    @GetMapping("/main/popular/{offset}/{limit}")
    @ResponseBody
    public List<RoomShowDto> roomMainPopularRoomsShows(@PathVariable int offset,@PathVariable int limit){
        return roomService.getPopularRooms(offset, limit);
    }
    //ajax 최근본 숙소리스트
    @GetMapping("/main/recent/")
    @ResponseBody
    public List<RoomShowDto> roomMainRecentCookieShows(HttpServletRequest request){
        return roomService.getRecentRooms(request);
    }
    //ajax 검색 숙소리스트
    @GetMapping("/main/search/{searchKeyword}/{offset}/{limit}")
    @ResponseBody
    public List<RoomShowDto> roomMainSearchShows(@PathVariable String searchKeyword,@PathVariable int offset,@PathVariable int limit){
        return roomService.getRoomShowBySearch(searchKeyword, offset, limit);
    }
//숙소검색페이지 //상품리스트보기
    @GetMapping("/search")
    public String search(@RequestParam String searchKeyword, Model model) {
        model.addAttribute("searchKeyword", searchKeyword);
        return "room/roomSearch";
    }
//지역,테마,인기숙소 페이지 //상품리스트보기
    @GetMapping("/{type}")
    public String roomType(@PathVariable String type,Model model){

        switch (type) {
            //지역
            case "region":
                List<LocationDto> locations = roomService.getAllLocations();
                model.addAttribute("locations", locations);
                model.addAttribute("type", "지역");
                break;
            //테마
            case "theme":
                List<ThemeDto> themes = roomService.getAllThemes();
                model.addAttribute("themes", themes);
                model.addAttribute("type", "테마");
                break;
            //인기숙소
            case "popular":
                model.addAttribute("type", "인기숙소");
                break;
        }

        return "room/roomType";
    }
    //숙소상세페이지 //상품보기
    @GetMapping("/detail/{roomId}")
    public String roomDetail(@PathVariable Long roomId, HttpServletRequest request, HttpServletResponse response,Model model){
        addRoomIdToCookie(roomId, request, response);
        RoomDetailDto roomDetailDto = roomService.getRoomDetail(roomId);
        model.addAttribute("room", roomDetailDto);
        return "room/roomDetail";
    }
    private void addRoomIdToCookie(Long roomId, HttpServletRequest request, HttpServletResponse response) {
        String cookieName = "recentRooms";
        Cookie cookie = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(cookieName))
                .findAny()
                .orElse(new Cookie(cookieName, ""));

        String value = cookie.getValue();
        if(!value.isEmpty()) value += "_";
        value += roomId.toString();

        cookie.setValue(value);
        cookie.setMaxAge(60 * 60 * 24 * 1); // 쿠키 유효기간 1일
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}