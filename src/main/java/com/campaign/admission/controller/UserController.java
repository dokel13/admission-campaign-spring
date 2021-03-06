package com.campaign.admission.controller;

import com.campaign.admission.domain.Role;
import com.campaign.admission.domain.User;
import com.campaign.admission.exception.ServiceRuntimeException;
import com.campaign.admission.exception.UserValidatorRuntimeException;
import com.campaign.admission.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Locale;

import static java.util.Objects.requireNonNull;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RequestMapping("/api")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Controller
@SessionAttributes("exception")
public class UserController {

    private static final String LOCALE_STRING = "locale=";

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/home")
    public ModelAndView home(Locale locale) {
        ModelAndView model = new ModelAndView();
        Role role = getRoleFromSecContext();
        if (role == Role.STUDENT) {
            model.setViewName("redirect:/api/student?" + LOCALE_STRING + locale);
        } else if (role == Role.ADMIN) {
            model.setViewName("redirect:/api/admin?" + LOCALE_STRING + locale);
        } else {
            model.setViewName("home");
        }

        return model;
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute("user") User newUser, BindingResult bindingResult,
                                 Locale locale) {
        ModelAndView model = new ModelAndView("redirect:/api/home?" + LOCALE_STRING + locale);
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

            UsernamePasswordAuthenticationToken authReq
                    = new UsernamePasswordAuthenticationToken(user.getEmail(), newUser.getPassword());
            Authentication auth = authenticationManager.authenticate(authReq);
            getContext().setAuthentication(auth);
        } catch (ServiceRuntimeException e) {
            model.addObject("exception", e);
        }

        return model;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "redirect:/api/home";
    }

    private Role getRoleFromSecContext() {
        Authentication authentication = getContext().getAuthentication();

        return authentication.getPrincipal().equals("anonymousUser") ? null
                : ((User) authentication.getPrincipal()).getRole();
    }
}
