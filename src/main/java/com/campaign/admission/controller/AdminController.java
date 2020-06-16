package com.campaign.admission.controller;

import com.campaign.admission.domain.Exam;
import com.campaign.admission.domain.User;
import com.campaign.admission.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.campaign.admission.util.PaginationUtils.countPages;
import static java.lang.Boolean.valueOf;
import static java.lang.Integer.parseInt;
import static java.util.Optional.ofNullable;

@RequestMapping("/api/admin")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class AdminController {

    private static final Integer PAGE_SIZE = 3;
    private static final String PAGE_STRING = "page=";

    private final AdminService adminService;

    @GetMapping
    public ModelAndView home() {
        ModelAndView model = new ModelAndView("admin/home");
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        model.addObject("name", user.getName());
        model.addObject("surname", user.getSurname());
        List<String> subjects = adminService.getAllSubjects();
        if (subjects != null) {
            model.addObject("subjects", subjects);
        }

        return model;
    }

    @GetMapping("/set_admission")
    public ModelAndView admission(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        Boolean open;
        if (request.getParameter("open") != null) {
            open = valueOf(request.getParameter("open"));
            adminService.setAdmission(open);
        }
        open = adminService.checkAdmission();
        model.setViewName("redirect:/api/admin?" + request.getQueryString()
                .replace("open=" + open + "&", ""));

        return model;
    }

    @GetMapping("/subject/{subject}")
    public ModelAndView subject(@PathVariable(value = "subject") String subject, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        int page = parseInt(ofNullable(request.getParameter("page")).orElse("1"));
        int pagesCount = countPages(PAGE_SIZE, adminService.countExamsBySubject(subject));
        if (page > pagesCount) {
            model.setViewName("redirect:/api/admin/subject/" + subject + "?" + request.getQueryString()
                    .replace(PAGE_STRING + page, PAGE_STRING + pagesCount));

            return model;
        }
        List<Exam> exams = adminService.getExamsPaginated(subject, page - 1, PAGE_SIZE);
        model.addObject("subject", subject);
        model.addObject("exams", exams);
        model.addObject("page", page);
        model.setViewName("admin/subject");

        return model;
    }

    @PostMapping("/subject/save_marks/{subject}")
    public String saveMarks(@PathVariable(value = "subject") String subject, HttpServletRequest request) {
        if (request.getParameterValues("mark") != null) {
            adminService.saveMarks(subject, request.getParameterValues("email"), request.getParameterValues("mark"));
        }

        return "redirect:/api/admin/subject/" + subject + "?" + request.getQueryString();
    }
}
