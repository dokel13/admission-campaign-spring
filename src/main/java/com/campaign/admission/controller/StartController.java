package com.campaign.admission.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class StartController {

    @RequestMapping("/")
    public String redirect() {
        return "redirect:/api/home";
    }
}
