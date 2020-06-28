package com.campaign.admission.service;

import com.campaign.admission.domain.Exam;
import com.campaign.admission.domain.User;
import com.campaign.admission.entity.ExamEntity;
import com.campaign.admission.entity.SpecialtyEntity;
import com.campaign.admission.entity.SubjectEntity;
import com.campaign.admission.entity.UserEntity;
import com.campaign.admission.repository.ApplicationRepository;
import com.campaign.admission.repository.ExamRepository;
import com.campaign.admission.repository.SpecialtyRepository;
import com.campaign.admission.repository.SubjectRepository;
import com.campaign.admission.service.mapper.ExamMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.PageRequest.of;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceImplTest {

    @Mock
    private ExamRepository examRepository;

    @Mock
    private SpecialtyRepository specialtyRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private ExamMapper examMapper;

    @InjectMocks
    private AdminServiceImpl adminService;

    private final SpecialtyEntity specialty = new SpecialtyEntity(
            "electronics", 20);

    private final SubjectEntity subjectEntity = new SubjectEntity("ukr");

    private final UserEntity userEntity = new UserEntity("email");

    private final ExamEntity examEntity = new ExamEntity(subjectEntity, userEntity);

    private final Exam exam = Exam.builder()
            .subject("ukr")
            .user(User.builder().email("email").build())
            .build();

    @Test
    public void getAllSubjectsShouldReturnNull() {
        when(specialtyRepository.findSpecialtiesOpens()).thenReturn(asList(false, false));
        assertThat(adminService.getAllSubjects(), is((List<String>) null));

        verify(specialtyRepository).findSpecialtiesOpens();
        verify(subjectRepository, never()).findAllSubjects();
    }

    @Test
    public void getAllSubjectsShouldReturnSubjectList() {
        List<String> subjects = asList("eng", "ukr");
        when(specialtyRepository.findSpecialtiesOpens()).thenReturn(asList(true, true));
        when(subjectRepository.findAllSubjects()).thenReturn(subjects);
        assertThat(adminService.getAllSubjects(), is(subjects));

        verify(specialtyRepository).findSpecialtiesOpens();
        verify(subjectRepository).findAllSubjects();
    }

    @Test
    public void setAdmissionShouldSetTrue() {
        adminService.setAdmission(true);

        verify(applicationRepository).setAllEnrollments(false);
        verify(specialtyRepository).setAdmission(true);
        verify(applicationRepository, never())
                .setEnrollmentsBySpecialties(anyBoolean(), anyString(), anyInt());
    }

    @Test
    public void setAdmissionShouldSetFalse() {
        List<SpecialtyEntity> specialties = asList(specialty, specialty, specialty);
        when(specialtyRepository.findAll()).thenReturn(specialties);
        adminService.setAdmission(false);

        verify(specialtyRepository).findAll();
        verify(applicationRepository, times(3))
                .setEnrollmentsBySpecialties(true, specialty.getSpecialty(),
                        specialty.getMaxStudentAmount());
        verify(specialtyRepository).setAdmission(false);
    }

    @Test
    public void getExamsPaginatedShouldReturnNull() {
        when(specialtyRepository.findSpecialtiesOpens()).thenReturn(asList(false, false));
        assertThat(adminService.getExamsPaginated("ukr", 0, 3),
                is((List<Exam>) null));
    }

    @Test
    public void getExamsPaginatedShouldReturnExamList() {
        PageRequest pageRequest = of(0, 3);
        PageImpl<ExamEntity> examsPage = new PageImpl<>(asList(examEntity, examEntity, examEntity),
                pageRequest, 6);
        when(specialtyRepository.findSpecialtiesOpens()).thenReturn(asList(true, true));
        when(examRepository.findBySubject(subjectEntity.getSubject(), pageRequest)).thenReturn(examsPage);
        when(examMapper.mapDomainFromEntity(examEntity)).thenReturn(exam);
        assertThat(adminService.getExamsPaginated("ukr", 0, 3),
                is((asList(exam, exam, exam))));

        verify(specialtyRepository).findSpecialtiesOpens();
        verify(examRepository).findBySubject(subjectEntity.getSubject(), pageRequest);
        verify(examMapper, times(3)).mapDomainFromEntity(examEntity);
    }

    @Test
    public void countExamsBySubjectShouldReturnInteger() {
        when(examRepository.countBySubjectAndApplicationIsNull(subjectEntity.getSubject())).thenReturn(3);
        assertThat(adminService.countExamsBySubject(subjectEntity.getSubject()), is(3));

        verify(examRepository).countBySubjectAndApplicationIsNull(subjectEntity.getSubject());
    }

    @Test
    public void saveMarksShouldSaveMarksByEmails() {
        String[] emails = {"email", "email"};
        String[] marks = {"160", "160", "160"};
        adminService.saveMarks(subjectEntity.getSubject(), emails, marks);

        verify(examRepository, times(2)).setMarks(parseInt(marks[0]),
                emails[0], subjectEntity.getSubject());
    }

    @Test
    public void checkAdmissionShouldReturnBoolean() {
        when(specialtyRepository.findSpecialtiesOpens()).thenReturn(asList(true, true));
        assertThat(adminService.checkAdmission(), is(true));

        verify(specialtyRepository).findSpecialtiesOpens();
    }
}
