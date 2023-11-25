package com.traveljoy.admin.controller;

import com.traveljoy.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Validated
@RequestMapping("/admin")
@Controller
public class AdminController {

    private final AdminService adminService;

    //관리자 메인 페이지
    @GetMapping("/main")
    public String adminMain(){
        return "admin/adminMain";
    }
    //관리자 숙소 등록 페이지
    @GetMapping("/roomReg")
    public String adminRoomReg(){
        return "admin/adminRoomReg";
    }
    //관리자 숙소 리스트 페이지
    @GetMapping("/roomList")
    public String adminRoomList(){
        return "admin/adminRoomList";
    }
    //관리자 숙소 등록
    //관리자 숙소 삭제
    //관리자 멤버 삭제

}
