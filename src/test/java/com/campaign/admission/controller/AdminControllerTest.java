package com.campaign.admission.controller;

import com.campaign.admission.configuration.handler.AuthenticationHandler;
import com.campaign.admission.configuration.handler.WrongAuthenticationHandler;
import com.campaign.admission.domain.Exam;
import com.campaign.admission.domain.Role;
import com.campaign.admission.domain.User;
import com.campaign.admission.service.AdminServiceImpl;
import com.campaign.admission.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.campaign.admission.domain.User.builder;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminServiceImpl adminService;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private AuthenticationHandler authenticationHandler;

    @MockBean
    private WrongAuthenticationHandler wrongAuthenticationHandler;

    private final User admin = builder()
            .email("email")
            .name("name")
            .surname("surname ")
            .password("password")
            .role(Role.ADMIN)
            .build();

    @Test
    public void homeShouldReturnAdminPage() throws Exception {
        List<String> subjects = asList("math", "physics");
        when(adminService.getAllSubjects()).thenReturn(subjects);
        mockMvc.perform(get("/api/admin?locale=ua")
                .with(user(admin)))
                .andExpect(view().name("admin/home"))
                .andExpect(model().attribute("name", admin.getName()))
                .andExpect(model().attribute("surname", admin.getSurname()))
                .andExpect(model().attribute("subjects", subjects))
                .andExpect(status().isOk());

        verify(adminService).getAllSubjects();
    }

    @Test
    public void admissionShouldRedirectAdminAndSetAdmission() throws Exception {
        when(adminService.checkAdmission()).thenReturn(true);
        mockMvc.perform(get("/api/admin/set_admission?open=true&locale=ua")
                .with(user(admin)))
                .andExpect(redirectedUrl("/api/admin?locale=ua"))
                .andExpect(status().is3xxRedirection());

        verify(adminService).setAdmission(true);
        verify(adminService).checkAdmission();
    }

    @Test
    public void admissionShouldRedirectAdmin() throws Exception {
        mockMvc.perform(get("/api/admin/set_admission?locale=ua")
                .with(user(admin)))
                .andExpect(redirectedUrl("/api/admin?locale=ua"))
                .andExpect(status().is3xxRedirection());

        verify(adminService, never()).setAdmission(anyBoolean());
        verify(adminService).checkAdmission();
    }

    @Test
    public void subjectShouldRedirectSubject() throws Exception {
        when(adminService.countExamsBySubject(anyString())).thenReturn(3);
        mockMvc.perform(get("/api/admin/subject/math?page=3&locale=ua")
                .with(user(admin)))
                .andExpect(redirectedUrl("/api/admin/subject/math?page=1&locale=ua"))
                .andExpect(model().attributeDoesNotExist("subject", "exams", "page"))
                .andExpect(status().is3xxRedirection());

        verify(adminService).countExamsBySubject("math");
        verify(adminService, never()).getExamsPaginated(anyString(), anyInt(), anyInt());
    }

    @Test
    public void subjectShouldReturnSubjectPage() throws Exception {
        List<Exam> exams = asList(new Exam(), new Exam());
        when(adminService.countExamsBySubject(anyString())).thenReturn(3);
        when(adminService.getExamsPaginated("math", 0, 3)).thenReturn(exams);
        mockMvc.perform(get("/api/admin/subject/math?page=1&locale=ua")
                .with(user(admin)))
                .andExpect(view().name("admin/subject"))
                .andExpect(model().attribute("subject", "math"))
                .andExpect(model().attribute("exams", exams))
                .andExpect(model().attribute("page", 1))
                .andExpect(status().isOk());

        verify(adminService).countExamsBySubject("math");
        verify(adminService).getExamsPaginated("math", 0, 3);
    }

    @Test
    public void saveMarksShouldRedirectSubjectAndSaveMarks() throws Exception {
        String[] email = {"email", "email"};
        String[] mark = {"mark", "mark"};
        mockMvc.perform(post("/api/admin/subject/save_marks/math?locale=ua")
                .param("email", email)
                .param("mark", mark)
                .with(user(admin)))
                .andExpect(redirectedUrl("/api/admin/subject/math?page=1&locale=ua"))
                .andExpect(status().is3xxRedirection());

        verify(adminService).saveMarks("math", email, mark);
    }
}
