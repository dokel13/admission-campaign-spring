package com.campaign.admission.controller;

import com.campaign.admission.configuration.handler.AuthenticationHandler;
import com.campaign.admission.configuration.handler.WrongAuthenticationHandler;
import com.campaign.admission.domain.Role;
import com.campaign.admission.domain.User;
import com.campaign.admission.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.campaign.admission.domain.User.builder;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private AuthenticationHandler authenticationHandler;

    @MockBean
    private WrongAuthenticationHandler wrongAuthenticationHandler;

    private final User user = builder()
            .name("name")
            .surname("surname")
            .email("email@ukr.net")
            .password("password")
            .role(Role.STUDENT)
            .build();

    private final User wrongUser = builder()
            .email("email")
            .password("password")
            .build();

    @Test
    public void homeShouldReturnHomePage() throws Exception {
        mockMvc.perform(get("/api/home?locale=ua"))
                .andExpect(view().name("home"))
                .andExpect(status().isOk());
    }

    @Test
    public void homeShouldRedirectStudent() throws Exception {
        mockMvc.perform(get("/api/home?locale=ua")
                .with(user(user)))
                .andExpect(redirectedUrl("/api/student?locale=ua"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void homeShouldRedirectAdmin() throws Exception {
        mockMvc.perform(get("/api/home?locale=ua")
                .with(user(User.builder().role(Role.ADMIN).build())))
                .andExpect(redirectedUrl("/api/admin?locale=ua"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void registerShouldRedirectHome() throws Exception {
        when(userService.register(user)).thenReturn(user);
        mockMvc.perform(post("/api/register?locale=ua")
                .flashAttr("user", user))
                .andExpect(redirectedUrl("/api/home?locale=ua"))
                .andExpect(status().is3xxRedirection());

        verify(userService).register(user);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void registerShouldRedirectHomeWithException() throws Exception {
        mockMvc.perform(post("/api/register?locale=ua")
                .flashAttr("user", wrongUser))
                .andExpect(redirectedUrl("/api/home?locale=ua"))
                .andExpect(model().attribute("exception",
                        hasProperty("message", is("Wrong email!"))))
                .andExpect(status().is3xxRedirection());

        verify(userService, never()).register(any(User.class));
        verify(authenticationManager, never()).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void logoutShouldRedirectHome() throws Exception {
        mockMvc.perform(get("/api/logout"))
                .andExpect(redirectedUrl("/api/home"))
                .andExpect(status().is3xxRedirection());
    }
}
