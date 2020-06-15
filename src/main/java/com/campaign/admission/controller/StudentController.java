package com.campaign.admission.controller;

import com.campaign.admission.domain.Application;
import com.campaign.admission.domain.Exam;
import com.campaign.admission.domain.Specialty;
import com.campaign.admission.domain.User;
import com.campaign.admission.exception.AdmissionValidatorRuntimeException;
import com.campaign.admission.exception.ServiceRuntimeException;
import com.campaign.admission.service.StudentService;
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
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.campaign.admission.util.PaginationUtils.countPages;
import static java.lang.Integer.parseInt;
import static java.util.Optional.ofNullable;

@RequestMapping("/api/student")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class StudentController {

    private static final Integer PAGE_SIZE = 3;
    private static final String PAGE_STRING = "page=";

    private final StudentService studentService;

    @GetMapping
    public ModelAndView home() {
        ModelAndView model = new ModelAndView("student/home");
        User user = getUserFromSecurity();
        Application application = studentService.getApplication(user.getEmail());
        Boolean enrollment;
        if (application != null) {
            enrollment = application.getEnrollment();
        } else {
            enrollment = false;
        }
        model.addObject("name", user.getName());
        model.addObject("surname", user.getSurname());
        model.addObject("enrollment", enrollment);

        return model;
    }

    @GetMapping("/subjects")
    public ModelAndView subjects() {
        ModelAndView model = new ModelAndView("student/subjects");
        try {
            List<String> subjects = studentService.getUserFreeSubjects(getUserFromSecurity().getEmail());
            model.addObject("subjects", subjects);
        } catch (ServiceRuntimeException e) {
            model.addObject("exception", e);
        }

        return model;
    }

    @PostMapping("/exams")
    public String examRegistration(HttpServletRequest request) {
        studentService.saveExamSubjects(request.getParameterValues("subject"), getUserFromSecurity().getEmail());

        return "redirect:/api/home?" + request.getQueryString();
    }

    @GetMapping("/specialties")
    public ModelAndView specialties() {
        ModelAndView model = new ModelAndView("student/specialties");
        List<String> specialties = studentService.getAllSpecialties();
        model.addObject("specialties", specialties);

        return model;
    }

    @GetMapping("/specialty/{specialty:\\D+\\.\\D+|\\D+}")
    public ModelAndView specialty(@PathVariable(value = "specialty") String specialtyName) {
        ModelAndView model = new ModelAndView("student/specialty");
        Specialty specialty = studentService.getSpecialty(specialtyName);
        model.addObject("specialty", specialty);

        return model;
    }

    @GetMapping("/specialty/apply/{specialty:\\D+\\.\\D+|\\D+}")
    public ModelAndView specialtyApply(@PathVariable(value = "specialty") String specialtyName, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        try {
            studentService.specialtyApply(getUserFromSecurity().getEmail(), specialtyName);
            model.setViewName("redirect:/api/home?" + request.getQueryString());
        } catch (ServiceRuntimeException | AdmissionValidatorRuntimeException e) {
            request.getSession().setAttribute("exception", e);
            model.setViewName("redirect:/api/student/specialty/" + specialtyName + "?" + request.getQueryString());
        }

        return model;
    }

    @GetMapping("/results")
    public ModelAndView results() {
        ModelAndView model = new ModelAndView("student/results");
        List<Exam> exams = studentService.getResults(getUserFromSecurity().getEmail());
        model.addObject("exams", exams);

        return model;
    }

    @GetMapping("/rating")
    public ModelAndView rating(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        int page = parseInt(ofNullable(request.getParameter("page")).orElse("1"));
        Application application = studentService.getApplication(getUserFromSecurity().getEmail());
        if (application == null) {
            model.setViewName("redirect:/api/home?" + request.getQueryString()
                    .replace(PAGE_STRING + page + "&", ""));

            return model;
        }
        String specialty = application.getSpecialty().getName();
        int pagesCount = countPages(PAGE_SIZE, studentService.countApplicationsBySpecialty(specialty));
        if (page > pagesCount) {
            model.setViewName("redirect:/api/student/rating?" + request.getQueryString()
                    .replace(PAGE_STRING + page, PAGE_STRING + pagesCount));

            return model;
        }
        List<Application> applications = studentService.getApplicationsPaginated(specialty, page - 1, PAGE_SIZE);
        model.addObject("specialty", specialty);
        model.addObject("apps", applications);
        model.addObject("page", page);
        model.setViewName("student/rating");

        return model;
    }

    private User getUserFromSecurity() {
        return (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
