package com.campaign.admission.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExceptionController implements ErrorController {

    @GetMapping("/api/error")
    @Override
    public String getErrorPath() {
        return "error_page";
    }
}
