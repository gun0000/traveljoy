package com.traveljoy.home.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Validated
@Controller
public class HomeController {

    @GetMapping("/")
    public String roomMain(){
        return "redirect:room/main";
    }
}
