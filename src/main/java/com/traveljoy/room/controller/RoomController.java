package com.traveljoy.room.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Validated
@RequestMapping("/room")
@Controller
public class RoomController {
//메인페이지 //상품리스트 여러개 보기
    @GetMapping("/main")
    public String roomMain(){
        return "room/roomMain";
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