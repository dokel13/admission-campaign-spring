package com.campaign.admission.controller;

import com.campaign.admission.configuration.handler.AuthenticationHandler;
import com.campaign.admission.configuration.handler.WrongAuthenticationHandler;
import com.campaign.admission.domain.*;
import com.campaign.admission.exception.AdmissionValidatorRuntimeException;
import com.campaign.admission.service.StudentServiceImpl;
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
@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentServiceImpl studentService;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private AuthenticationHandler authenticationHandler;

    @MockBean
    private WrongAuthenticationHandler wrongAuthenticationHandler;

    private final User student = builder()
            .email("email")
            .name("name")
            .surname("surname ")
            .password("password")
            .role(Role.STUDENT)
            .build();

    private final Specialty specialty = Specialty.builder()
            .name("computer.technologies")
            .build();

    private final Application application = Application.builder()
            .specialty(specialty)
            .enrollment(true)
            .build();

    @Test
    public void homeShouldReturnStudentPage() throws Exception {
        when(studentService.getApplication(student.getEmail())).thenReturn(application);
        mockMvc.perform(get("/api/student?locale=ua")
                .with(user(student)))
                .andExpect(view().name("student/home"))
                .andExpect(model().attribute("name", student.getName()))
                .andExpect(model().attribute("surname", student.getSurname()))
                .andExpect(model().attribute("enrollment", application.getEnrollment()))
                .andExpect(status().isOk());

        verify(studentService).getApplication(student.getEmail());
    }

    @Test
    public void subjectsShouldReturnSubjectsPage() throws Exception {
        List<String> subjects = asList("math", "eng");
        when(studentService.getUserFreeSubjects(student.getEmail())).thenReturn(subjects);
        mockMvc.perform(get("/api/student/subjects?locale=ua")
                .with(user(student)))
                .andExpect(view().name("student/subjects"))
                .andExpect(model().attribute("subjects", subjects))
                .andExpect(status().isOk());

        verify(studentService).getUserFreeSubjects(student.getEmail());
    }

    @Test
    public void examRegistrationShouldRedirectHome() throws Exception {
        String[] subject = {"math", "eng"};
        mockMvc.perform(post("/api/student/exams?locale=ua")
                .param("subject", subject)
                .with(user(student)))
                .andExpect(redirectedUrl("/api/home?locale=ua"))
                .andExpect(status().is3xxRedirection());

        verify(studentService).saveExamSubjects(subject, student.getEmail());
    }

    @Test
    public void specialtiesShouldReturnSpecialtiesPage() throws Exception {
        List<String> specialties = asList("linguistics", "mech.math");
        when(studentService.getAllSpecialties()).thenReturn(specialties);
        mockMvc.perform(get("/api/student/specialties?locale=ua")
                .with(user(student)))
                .andExpect(view().name("student/specialties"))
                .andExpect(model().attribute("specialties", specialties))
                .andExpect(status().isOk());

        verify(studentService).getAllSpecialties();
    }

    @Test
    public void specialtyShouldReturnSpecialtyPage() throws Exception {
        when(studentService.getSpecialty(specialty.getName())).thenReturn(specialty);
        mockMvc.perform(get("/api/student/specialty/computer.technologies?locale=ua")
                .with(user(student)))
                .andExpect(view().name("student/specialty"))
                .andExpect(model().attribute("specialty", specialty))
                .andExpect(status().isOk());

        verify(studentService).getSpecialty(specialty.getName());
    }

    @Test
    public void specialtyApplyShouldRedirectHome() throws Exception {
        mockMvc.perform(get("/api/student/specialty/apply/mech.math?locale=ua")
                .with(user(student)))
                .andExpect(redirectedUrl("/api/home?locale=ua"))
                .andExpect(status().is3xxRedirection());

        verify(studentService).specialtyApply(student.getEmail(), "mech.math");
    }

    @Test
    public void specialtyApplyShouldRedirectSpecialty() throws Exception {
        Exception exception = new AdmissionValidatorRuntimeException();
        when(studentService.specialtyApply(student.getEmail(), "mech.math"))
                .thenThrow(exception);
        mockMvc.perform(get("/api/student/specialty/apply/mech.math?locale=ua")
                .with(user(student)))
                .andExpect(redirectedUrl("/api/student/specialty/mech.math?locale=ua"))
                .andExpect(request().sessionAttribute("exception", exception))
                .andExpect(status().is3xxRedirection());

        verify(studentService).specialtyApply(student.getEmail(), "mech.math");
    }

    @Test
    public void resultsShouldReturnResultPage() throws Exception {
        List<Exam> exams = asList(new Exam(), new Exam());
        when(studentService.getResults(student.getEmail())).thenReturn(exams);
        mockMvc.perform(get("/api/student/results?locale=ua")
                .with(user(student)))
                .andExpect(view().name("student/results"))
                .andExpect(model().attribute("exams", exams))
                .andExpect(status().isOk());

        verify(studentService).getResults(student.getEmail());
    }

    @Test
    public void ratingShouldRedirectHome() throws Exception {
        mockMvc.perform(get("/api/student/rating?page=1&locale=ua")
                .with(user(student)))
                .andExpect(redirectedUrl("/api/home?locale=ua"))
                .andExpect(model().attributeDoesNotExist("specialty", "apps", "page"))
                .andExpect(status().is3xxRedirection());

        verify(studentService).getApplication(student.getEmail());
        verify(studentService, never()).countApplicationsBySpecialty(anyString());
        verify(studentService, never()).getApplicationsPaginated(anyString(), anyInt(), anyInt());
    }

    @Test
    public void ratingShouldRedirectRating() throws Exception {
        when(studentService.getApplication(student.getEmail())).thenReturn(application);
        when(studentService.countApplicationsBySpecialty(specialty.getName())).thenReturn(3);
        mockMvc.perform(get("/api/student/rating?page=3&locale=ua")
                .with(user(student)))
                .andExpect(redirectedUrl("/api/student/rating?page=1&locale=ua"))
                .andExpect(model().attributeDoesNotExist("specialty", "apps", "page"))
                .andExpect(status().is3xxRedirection());

        verify(studentService).getApplication(student.getEmail());
        verify(studentService).countApplicationsBySpecialty(specialty.getName());
        verify(studentService, never()).getApplicationsPaginated(anyString(), anyInt(), anyInt());
    }

    @Test
    public void ratingShouldReturnRatingPage() throws Exception {
        List<Application> applications = asList(new Application(), new Application());
        when(studentService.getApplication(student.getEmail())).thenReturn(application);
        when(studentService.countApplicationsBySpecialty(specialty.getName())).thenReturn(3);
        when(studentService.getApplicationsPaginated(specialty.getName(), 0, 3)).thenReturn(applications);
        mockMvc.perform(get("/api/student/rating?page=1&locale=ua")
                .with(user(student)))
                .andExpect(view().name("student/rating"))
                .andExpect(model().attribute("specialty", specialty.getName()))
                .andExpect(model().attribute("apps", applications))
                .andExpect(model().attribute("page", 1))
                .andExpect(status().isOk());

        verify(studentService).getApplication(student.getEmail());
        verify(studentService).countApplicationsBySpecialty(specialty.getName());
        verify(studentService).getApplicationsPaginated(specialty.getName(), 0, 3);
    }
}
