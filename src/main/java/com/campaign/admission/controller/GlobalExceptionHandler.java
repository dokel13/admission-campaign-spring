package com.campaign.admission.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public String errorHandler(HttpServletRequest request) {
        return "redirect:/api/error?" + request.getQueryString();
    }
}
