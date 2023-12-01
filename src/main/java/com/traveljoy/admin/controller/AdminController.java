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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
    //관리자 숙소 등록
    @PostMapping("/roomReg")
    public String adminRoomRegPost(RoomDto roomDto, @RequestParam("image") MultipartFile[] files, Model model)throws IOException {
        //files은 사용자가 업로드한 파일들의 배열
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
    //관리자 숙소 리스트 페이지
    @GetMapping("/roomList/{page}/{size}/")
    public String adminRoomList(
            @PathVariable int page, @PathVariable int size,
            @RequestParam(required = false) String searchType, @RequestParam(required = false) String keyword,
            Model model){
        Pageable pageable = PageRequest.of(page, size);
        Page<AdminRoomListDto> rooms;
        if (searchType != null && keyword != null) {
            rooms = roomService.findRoomWithLocationAndThemeListByPageAndSearch(pageable, searchType, keyword);
        } else {
            rooms = roomService.findRoomWithLocationAndThemeListByPage(pageable);
        }
        model.addAttribute("rooms", rooms);
        Long lastItemNumber = Math.min((long) (rooms.getNumber() + 1) * rooms.getSize(), rooms.getTotalElements());
        model.addAttribute("lastItemNumber", lastItemNumber);
        model.addAttribute("searchType", searchType); // 검색 유형
        model.addAttribute("keyword", keyword); // 검색어
        return "admin/adminRoomList";
    }
    //관리자 숙소 삭제
    @DeleteMapping("/room/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteRoom(@PathVariable Long id) {

        try {
            roomService.deleteRoom(id);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //관리자 숙소 수정 페이지
    @GetMapping("/room/{id}/edit/")
    public String edit(@PathVariable Long id, Model model) {
        List<LocationDto> locations = roomService.getAllLocations();
        List<ThemeDto> themes = roomService.getAllThemes();
        try {
            RoomDto room = roomService.getRoomAndImagesListById(id); // roomService에서 숙소 번호로 숙소 정보를 가져오는 메소드 호출
            model.addAttribute("room", room);
            model.addAttribute("isSuccess", true);
        } catch (Exception e) {
            model.addAttribute("isSuccess", false);
        }
        model.addAttribute("locations", locations);
        model.addAttribute("themes", themes);
        return "admin/adminRoomEdit";
    }
    //관리자 숙소 수정
    @PostMapping("/room/{id}/edit/")
    public String updateRoom(@PathVariable Long id,RoomDto roomDto,
                             RedirectAttributes redirectAttributes,
                             @RequestParam Map<String, MultipartFile> files
                             )throws IOException {
        roomDto.setId(id);
        String uploadDir = "src/main/resources/static/images/roomimage"; // 파일을 저장할 디렉토리의 경로
        //files은 사용자가 업로드한 파일들의 배열
        for (Map.Entry<String, MultipartFile> entry : files.entrySet()) {
            String key = entry.getKey();
            String numberPart = key.replaceAll("\\D+", ""); // 숫자가 아닌 모든 문자를 제거
            Long imageId = Long.parseLong(numberPart); // 문자열을 Long으로 변환
            MultipartFile file = entry.getValue();

            if (!file.isEmpty()) { // 파일이 있는 경우에만 파일을 저장
                // 각 파일을 서버에 저장하고, 그 경로를 roomDto의 images 리스트에 추가
                String originalFilename = file.getOriginalFilename();
                String filename = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
                String filepath = uploadDir + "/" + filename; // 저장할 파일의 전체 경로
                try {
                    Files.copy(file.getInputStream(), Paths.get(filepath)); // 파일을 저장
                } catch (Exception e) {
                    // 파일 복사 실패 처리
                }
                roomDto.getImages().add(filename);
                roomDto.getImageId().add(imageId); // 이미지의 ID를 리스트에 추가
            }
        }
        try {
            roomService.updateRoom(roomDto);
            redirectAttributes.addFlashAttribute("updateSuccess", true);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("updateSuccess", false);
        }
        return "redirect:/admin/room/"+id+"/edit/";
    }
    //관리자 멤버 삭제

}
