package com.campaign.admission.controller;

import com.campaign.admission.domain.Role;
import com.campaign.admission.domain.User;
import com.campaign.admission.exception.ServiceRuntimeException;
import com.campaign.admission.exception.UserValidatorRuntimeException;
import com.campaign.admission.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static java.util.Objects.requireNonNull;

@RequestMapping("/api")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Controller
@SessionAttributes("exception")
public class UserController {

    private final UserService userService;

    @GetMapping("/home")
    public ModelAndView home(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        Role role = (Role) request.getSession().getAttribute("role");
        if (role == Role.STUDENT) {
            model.setViewName("redirect:/api/student?" + request.getQueryString());
        } else if (role == Role.ADMIN) {
            model.setViewName("redirect:/api/admin?" + request.getQueryString());
        } else {
            model.setViewName("home");
        }

        return model;
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute("user") User newUser, BindingResult bindingResult,
                                 HttpServletRequest request) {
        ModelAndView model = new ModelAndView("redirect:/api/home?" + request.getQueryString());
        if (bindingResult.hasErrors()) {
            model.addObject("exception", new UserValidatorRuntimeException(requireNonNull(bindingResult
                    .getFieldError("email")).getDefaultMessage()));

            return model;
        }
        try {
            User user = userService.register(User.builder()
                    .role(Role.STUDENT)
                    .email(newUser.getEmail())
                    .name(newUser.getName())
                    .surname(newUser.getSurname())
                    .password(newUser.getPassword())
                    .build());

            request.setAttribute("email", user.getEmail());
        } catch (ServiceRuntimeException e) {
            model.addObject("exception", e);

            return model;
        }
        model.setViewName("redirect:/api/login?" + request.getQueryString());

        return model;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "redirect:/api/home";
    }
}
