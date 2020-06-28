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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ExceptionController.class)
public class ExceptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private AuthenticationHandler authenticationHandler;

    @MockBean
    private WrongAuthenticationHandler wrongAuthenticationHandler;

    @Test
    public void getErrorPathShouldReturnErrorPage() throws Exception {
        mockMvc.perform(get("/api/error?locale=en"))
                .andExpect(view().name("error_page"))
                .andExpect(status().isOk());
    }
}
