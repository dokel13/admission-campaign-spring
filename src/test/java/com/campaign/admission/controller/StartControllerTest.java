package com.campaign.admission.controller;

import com.campaign.admission.configuration.handler.AuthenticationHandler;
import com.campaign.admission.configuration.handler.WrongAuthenticationHandler;
import com.campaign.admission.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StartController.class)
public class StartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private AuthenticationHandler authenticationHandler;

    @MockBean
    private WrongAuthenticationHandler wrongAuthenticationHandler;

    @Test
    public void redirectShouldRedirectHome() throws Exception {
        mockMvc.perform(get("/?locale=ua"))
                .andExpect(redirectedUrl("/api/home?locale=ua"))
                .andExpect(status().is3xxRedirection());
    }
}
