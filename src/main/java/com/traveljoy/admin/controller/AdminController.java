package com.traveljoy.admin.controller;

import com.traveljoy.admin.dto.AdminRoomListDto;
import com.traveljoy.admin.service.AdminService;
import com.traveljoy.room.dto.LocationDto;
import com.traveljoy.room.dto.RoomDto;
import com.traveljoy.room.dto.ThemeDto;
import com.traveljoy.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Validated
@Secured({"ROLE_ADMIN"})
@RequestMapping("/admin")
@Controller
public class AdminController {

    private final AdminService adminService;
    private final RoomService roomService;

    //관리자 메인 페이지
    @GetMapping("/main")
    public String adminMain(){
        return "admin/adminMain";
    }
    //관리자 숙소 등록 페이지
    @GetMapping("/roomReg")
    public String adminRoomReg(Model model){
        List<LocationDto> locations = roomService.getAllLocations();
        List<ThemeDto> themes = roomService.getAllThemes();

        model.addAttribute("locations", locations);
        model.addAttribute("themes", themes);
        return "admin/adminRoomReg";
    }
    //관리자 숙소 리스트 페이지
    @GetMapping("/roomList/{page}/{size}/")
    public String adminRoomList(@PathVariable int page, @PathVariable int size, Model model){
        Pageable pageable = PageRequest.of(page, size);
        Page<AdminRoomListDto> rooms = roomService.findRoomWithLocationAndThemeListByPage(pageable);
        model.addAttribute("rooms", rooms);
        Long lastItemNumber = Math.min((long) (rooms.getNumber() + 1) * rooms.getSize(), rooms.getTotalElements());
        model.addAttribute("lastItemNumber", lastItemNumber);
        return "admin/adminRoomList";
    }
    //관리자 숙소 등록
    @PostMapping("/roomReg")
    public String adminRoomRegPost(RoomDto roomDto, @RequestParam("image") MultipartFile[] files, Model model)throws IOException {
        // 여기서 files는 사용자가 업로드한 파일들의 배열입니다.
        for (MultipartFile file : files) {
            if (!file.isEmpty()) { // 파일이 있는 경우에만 파일을 저장
                // 각 파일을 서버에 저장하고, 그 경로를 roomDto의 images 리스트에 추가
                String uploadDir = "src/main/resources/static/images/roomimage";// 파일을 저장할 디렉토리의 경로
                String originalFilename = file.getOriginalFilename();
                String filename = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
                String filepath = uploadDir + "/" + filename; // 저장할 파일의 전체 경로
                Files.copy(file.getInputStream(), Paths.get(filepath)); // 파일을 저장
                roomDto.getImages().add(filename);
            }
        }
        try {
            roomService.createRoom(roomDto);
            model.addAttribute("isSuccess", true);
        } catch (Exception e) {
            model.addAttribute("isSuccess", false);
        }
        return "admin/adminRoomReg";
    }
    //관리자 숙소 삭제
    //관리자 멤버 삭제

}
