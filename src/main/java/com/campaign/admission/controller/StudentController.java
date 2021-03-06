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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

import static com.campaign.admission.util.PaginationUtils.countPages;
import static java.lang.Integer.parseInt;
import static java.util.Optional.ofNullable;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RequestMapping("/api/student")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class StudentController {

    private static final Integer PAGE_SIZE = 3;
    private static final String PAGE_STRING = "page=";
    private static final String LOCALE_STRING = "locale=";

    private final StudentService studentService;

    @GetMapping
    public ModelAndView home() {
        ModelAndView model = new ModelAndView("student/home");
        User user = getUserFromSecContext();
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
            List<String> subjects = studentService.getUserFreeSubjects(getUserFromSecContext().getEmail());
            model.addObject("subjects", subjects);
        } catch (ServiceRuntimeException e) {
            model.addObject("exception", e);
        }

        return model;
    }

    @PostMapping("/exams")
    public String examRegistration(@RequestParam(value = "subject", required = false) String[] subject,
                                   Locale locale) {
        if (subject != null) {
            studentService.saveExamSubjects(subject, getUserFromSecContext().getEmail());
        }

        return "redirect:/api/home?" + LOCALE_STRING + locale;
    }

    @GetMapping("/specialties")
    public ModelAndView specialties() {
        ModelAndView model = new ModelAndView("student/specialties");
        List<String> specialties = studentService.getAllSpecialties();
        model.addObject("specialties", specialties);

        return model;
    }

    @GetMapping("/specialty/{specialty:\\D+\\.\\D+|\\D+}")
    public ModelAndView specialty(@PathVariable("specialty") String specialtyName) {
        ModelAndView model = new ModelAndView("student/specialty");
        Specialty specialty = studentService.getSpecialty(specialtyName);
        model.addObject("specialty", specialty);

        return model;
    }

    @GetMapping("/specialty/apply/{specialty:\\D+\\.\\D+|\\D+}")
    public ModelAndView specialtyApply(@PathVariable("specialty") String specialtyName, Locale locale,
                                       HttpSession session) {
        ModelAndView model = new ModelAndView();
        try {
            studentService.specialtyApply(getUserFromSecContext().getEmail(), specialtyName);
            model.setViewName("redirect:/api/home?" + LOCALE_STRING + locale);
        } catch (ServiceRuntimeException | AdmissionValidatorRuntimeException e) {
            session.setAttribute("exception", e);
            model.setViewName("redirect:/api/student/specialty/" + specialtyName + "?" + LOCALE_STRING + locale);
        }

        return model;
    }

    @GetMapping("/results")
    public ModelAndView results() {
        ModelAndView model = new ModelAndView("student/results");
        List<Exam> exams = studentService.getResults(getUserFromSecContext().getEmail());
        model.addObject("exams", exams);

        return model;
    }

    @GetMapping("/rating")
    public ModelAndView rating(@RequestParam(value = "page", required = false) String pageParam, Locale locale) {
        ModelAndView model = new ModelAndView();
        int page = parseInt(ofNullable(pageParam).orElse("1"));
        Application application = studentService.getApplication(getUserFromSecContext().getEmail());
        if (application == null) {
            model.setViewName("redirect:/api/home?" + LOCALE_STRING + locale);

            return model;
        }
        String specialty = application.getSpecialty().getName();
        int pagesCount = countPages(PAGE_SIZE, studentService.countApplicationsBySpecialty(specialty));
        if (page > pagesCount) {
            model.setViewName("redirect:/api/student/rating?" + PAGE_STRING + pagesCount +
                    "&" + LOCALE_STRING + locale);

            return model;
        }
        List<Application> applications = studentService.getApplicationsPaginated(specialty, page - 1, PAGE_SIZE);
        model.addObject("specialty", specialty);
        model.addObject("apps", applications);
        model.addObject("page", page);
        model.setViewName("student/rating");

        return model;
    }

    private User getUserFromSecContext() {
        return (User) getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
