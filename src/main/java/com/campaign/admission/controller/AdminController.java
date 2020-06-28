package com.campaign.admission.controller;

import com.campaign.admission.domain.Exam;
import com.campaign.admission.domain.User;
import com.campaign.admission.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.campaign.admission.util.PaginationUtils.countPages;
import static java.lang.Boolean.valueOf;
import static java.lang.Integer.parseInt;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

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
    public ModelAndView admission(@RequestParam(value = "open", required = false) String openParam,
                                  HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        Boolean open;
        if (openParam != null) {
            open = valueOf(openParam);
            adminService.setAdmission(open);
        }
        open = adminService.checkAdmission();
        model.setViewName("redirect:/api/admin?" + request.getQueryString()
                .replace("open=" + open + "&", ""));

        return model;
    }

    @GetMapping("/subject/{subject}")
    public ModelAndView subject(@PathVariable("subject") String subject,
                                @RequestParam("page") String pageParam, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        int page = parseInt(ofNullable(pageParam).orElse("1"));
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
    public String saveMarks(@PathVariable("subject") String subject,
                            @RequestParam(value = "email", required = false) String[] email,
                            @RequestParam(value = "mark", required = false) String[] mark,
                            HttpServletRequest request) {
        if (mark != null) {
            adminService.saveMarks(subject, email, mark);
        }

        return "redirect:/api/admin/subject/" + subject + "?" + request.getQueryString();
    }
}
