package com.campaign.admission.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

@Controller
public class StartController {

    @RequestMapping("/")
    public String redirect(Locale locale) {
        return "redirect:/api/home?locale=" + locale;
    }
}
