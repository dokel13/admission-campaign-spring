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
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Locale;

@SessionAttributes({"role", "email", "name", "surname", "exception"})
@RequestMapping("/api")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/home")
    public ModelAndView home(ModelAndView model) {
        Role role = (Role) model.getModel().get("role");
        if (role == Role.STUDENT) {
            model.setViewName("redirect:/admission/api/student");
        } else if (role == Role.ADMIN) {
            model.setViewName("redirect:/admission/api/admin");
        } else {
            model.setViewName("home");
        }

        return model;
    }

    @GetMapping("/register")
    public String register() {

        return "redirect:/home";
    }

    @PostMapping("/login")
    public ModelAndView login(@Valid @ModelAttribute("user") User newUser, BindingResult bindingResult, ModelAndView model) {
        if (bindingResult.hasErrors()) {
            model.setViewName("redirect:/api/home");

            return model;
        }
        try {
            User user = userService.login(User.builder()
                    .email(newUser.getEmail())
                    .password(newUser.getPassword())
                    .build());

            model.addObject("role", user.getRole());
            model.addObject("email", user.getEmail());
            model.addObject("name", user.getName());
            model.addObject("surname", user.getSurname());
        } catch (ServiceRuntimeException | UserValidatorRuntimeException e) {
            model.addObject("exception", e);
        }
        model.setViewName("redirect:/api/home");

        return model;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "redirect:/admission/api/home";
    }
}
