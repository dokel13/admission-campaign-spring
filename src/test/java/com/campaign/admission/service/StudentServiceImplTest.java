package com.campaign.admission.service;

import com.campaign.admission.domain.*;
import com.campaign.admission.entity.*;
import com.campaign.admission.exception.ServiceRuntimeException;
import com.campaign.admission.repository.*;
import com.campaign.admission.service.mapper.ApplicationMapper;
import com.campaign.admission.service.mapper.ExamMapper;
import com.campaign.admission.service.mapper.SpecialtyMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.PageRequest.of;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceImplTest {

    @Mock
    private ExamRepository examRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private SpecialtyRepository specialtyRepository;

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private SpecialtyMapper specialtyMapper;

    @Mock
    private ExamMapper examMapper;

    @Mock
    private ApplicationMapper applicationMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Rule
    public ExpectedException expectedException = none();

    private final User user = User.builder()
            .email("email")
            .build();

    private final UserEntity userEntity = new UserEntity(user.getEmail());

    private final Application application = Application.builder().markSum(540).build();

    private final ApplicationEntity applicationEntity = new ApplicationEntity();

    private final PageRequest pageRequest = of(3, 3);

    private final List<ExamEntity> examEntities = asList(new ExamEntity(), new ExamEntity(), new ExamEntity());

    private final List<Exam> exams = asList(Exam.builder().subject("math").mark(180).build(),
            Exam.builder().subject("eng").mark(180).build(),
            Exam.builder().subject("physics").mark(180).build());

    private Specialty specialty;

    private SpecialtyEntity specialtyEntity;

    @Before
    public void setSpecialties() {
        specialty = Specialty.builder().name("telecommunications").open(true).build();
        specialtyEntity = new SpecialtyEntity(specialty.getName());
    }

    @Test
    public void getUserFreeSubjectsShouldThrowAdmissionException() {
        expectedException.expect(ServiceRuntimeException.class);
        expectedException.expectMessage("Admission is closed!");
        when(specialtyRepository.findSpecialtiesOpens()).thenReturn(asList(false, false));
        try {
            studentService.getUserFreeSubjects(user.getEmail());
        } finally {
            verify(specialtyRepository).findSpecialtiesOpens();
            verify(subjectRepository, never()).findUserFreeSubjects(anyString());
        }
    }

    @Test
    public void getUserFreeSubjectsShouldThrowNullSubjectsException() {
        expectedException.expect(ServiceRuntimeException.class);
        expectedException.expectMessage("Finding subjects exception! No user free subjects are found!");
        when(specialtyRepository.findSpecialtiesOpens()).thenReturn(asList(true, true));
        try {
            studentService.getUserFreeSubjects(user.getEmail());
        } finally {
            verify(specialtyRepository).findSpecialtiesOpens();
            verify(subjectRepository).findUserFreeSubjects(user.getEmail());
        }
    }

    @Test
    public void getUserFreeSubjectsShouldReturnList() {
        when(specialtyRepository.findSpecialtiesOpens()).thenReturn(asList(true, true));
        when(subjectRepository.findUserFreeSubjects(user.getEmail())).thenReturn(asList("eng", "physics"));
        try {
            studentService.getUserFreeSubjects(user.getEmail());
        } finally {
            verify(specialtyRepository).findSpecialtiesOpens();
            verify(subjectRepository).findUserFreeSubjects(user.getEmail());
        }
    }

    @Test
    public void saveExamSubjectsShouldDoNothing() {
        studentService.saveExamSubjects(null, user.getEmail());

        verify(examRepository, never()).save(any(ExamEntity.class));
        verify(examMapper, never()).mapEntityFromDomain(any(SubjectEntity.class), any(UserEntity.class));
        verify(subjectRepository, never()).findBySubject(anyString());
        verify(userRepository, never()).findByEmail(anyString());
    }

    @Test
    public void saveExamSubjectsShouldSaveSubjects() {
        String[] subjects = {"ukr", "ukr", "ukr", "ukr"};
        SubjectEntity subjectEntity = new SubjectEntity(subjects[0]);
        ExamEntity examEntity = new ExamEntity(subjectEntity, userEntity);
        when(subjectRepository.findBySubject(subjects[0])).thenReturn(subjectEntity);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(userEntity);
        when(examMapper.mapEntityFromDomain(subjectEntity, userEntity)).thenReturn(examEntity);
        studentService.saveExamSubjects(subjects, user.getEmail());

        verify(examRepository, times(4)).save(examEntity);
        verify(examMapper, times(4)).mapEntityFromDomain(subjectEntity, userEntity);
        verify(subjectRepository, times(4)).findBySubject(subjects[0]);
        verify(userRepository, times(4)).findByEmail(user.getEmail());
    }

    @Test
    public void getAllSpecialtiesShouldReturnList() {
        List<String> specialties = asList("electronics", "mech.math");
        when(specialtyRepository.findAllSpecialtiesNames()).thenReturn(specialties);
        assertThat(studentService.getAllSpecialties(), is(specialties));

        verify(specialtyRepository).findAllSpecialtiesNames();
    }

    @Test
    public void getSpecialtyShouldReturnSpecialty() {
        when(specialtyRepository.findBySpecialty(specialty.getName())).thenReturn(specialtyEntity);
        when(specialtyMapper.mapDomainFromEntity(specialtyEntity)).thenReturn(specialty);
        assertThat(studentService.getSpecialty(specialty.getName()), is(specialty));

        verify(specialtyMapper).mapDomainFromEntity(specialtyEntity);
        verify(specialtyRepository).findBySpecialty(specialty.getName());
    }

    @Test
    public void getResultsShouldReturnExamList() {
        when(examMapper.mapDomainFromEntity(examEntities.get(0))).thenReturn(exams.get(0), exams.get(1), exams.get(2));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(userEntity);
        when(examRepository.findByUser(userEntity)).thenReturn(examEntities);
        assertThat(studentService.getResults(user.getEmail()), is(exams));

        verify(examMapper, times(3)).mapDomainFromEntity(examEntities.get(0));
        verify(userRepository).findByEmail(user.getEmail());
        verify(examRepository).findByUser(userEntity);
    }

    @Test
    public void getApplicationShouldReturnNull() {
        when(specialtyRepository.findSpecialtiesOpens()).thenReturn(asList(true, true));
        assertThat(studentService.getApplication(user.getEmail()), is((Application) null));

        verify(specialtyRepository).findSpecialtiesOpens();
        verify(applicationMapper, never()).mapDomainFromEntity(any(ApplicationEntity.class));
        verify(applicationRepository, never()).findByUser(any(UserEntity.class));
        verify(userRepository, never()).findByEmail(anyString());
    }

    @Test
    public void getApplicationShouldReturnApplication() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(userEntity);
        when(applicationRepository.findByUser(userEntity)).thenReturn(applicationEntity);
        when(applicationMapper.mapDomainFromEntity(applicationEntity)).thenReturn(application);
        when(specialtyRepository.findSpecialtiesOpens()).thenReturn(asList(false, false));
        assertThat(studentService.getApplication(user.getEmail()), is(application));

        verify(specialtyRepository).findSpecialtiesOpens();
        verify(applicationMapper).mapDomainFromEntity(applicationEntity);
        verify(applicationRepository).findByUser(userEntity);
        verify(userRepository).findByEmail(user.getEmail());
    }

    @Test
    public void countApplicationsBySpecialtyShouldReturnInteger() {
        when(specialtyRepository.findBySpecialty(specialtyEntity.getSpecialty()))
                .thenReturn(specialtyEntity);
        when(applicationRepository.countBySpecialty(specialtyEntity)).thenReturn(13);
        assertThat(studentService.countApplicationsBySpecialty(specialtyEntity.getSpecialty()), is(13));

        verify(specialtyRepository).findBySpecialty(specialtyEntity.getSpecialty());
        verify(applicationRepository).countBySpecialty(specialtyEntity);
    }

    @Test
    public void getApplicationsPaginatedShouldReturnNull() {
        when(applicationRepository.findBySpecialtyOrderByMarkSumDesc(null, pageRequest))
                .thenReturn(new PageImpl<>(new ArrayList<>(), pageRequest, 3));
        assertThat(studentService.getApplicationsPaginated(specialty.getName(), 3, 3),
                is((List<Application>) null));

        verify(specialtyRepository).findBySpecialty(specialty.getName());
        verify(applicationRepository).findBySpecialtyOrderByMarkSumDesc(null, pageRequest);
        verify(applicationMapper, never()).mapDomainFromEntity(any(ApplicationEntity.class));
    }

    @Test
    public void getApplicationsPaginatedShouldApplicationList() {
        List<Application> applications = asList(application, application);
        PageImpl<ApplicationEntity> page = new PageImpl<>(asList(applicationEntity, applicationEntity),
                pageRequest, 3);
        when(specialtyRepository.findBySpecialty(specialty.getName())).thenReturn(specialtyEntity);
        when(applicationRepository.findBySpecialtyOrderByMarkSumDesc(specialtyEntity, pageRequest))
                .thenReturn(page);
        when(applicationMapper.mapDomainFromEntity(applicationEntity)).thenReturn(application);
        assertThat(studentService.getApplicationsPaginated(specialty.getName(), 3, 3),
                is(applications));

        verify(specialtyRepository).findBySpecialty(specialty.getName());
        verify(applicationRepository).findBySpecialtyOrderByMarkSumDesc(specialtyEntity, pageRequest);
        verify(applicationMapper, times(2)).mapDomainFromEntity(any(ApplicationEntity.class));
    }

    @Test
    public void specialtyApplyShouldThrowAdmissionException() {
        specialty.setOpen(false);
        expectedException.expect(ServiceRuntimeException.class);
        expectedException.expectMessage("Admission is closed!");
        when(specialtyMapper.mapDomainFromEntity(null)).thenReturn(specialty);
        studentService.specialtyApply(user.getEmail(), specialty.getName());

        verify(specialtyRepository).findBySpecialty(specialtyEntity.getSpecialty());
        verify(specialtyMapper).mapDomainFromEntity(null);
        verify(userRepository, never()).findByEmail(anyString());
        verify(applicationRepository, never()).findByUser(any(UserEntity.class));
        verify(examRepository, never()).findByUser(any(UserEntity.class));
        verify(examMapper, never()).mapDomainFromEntity(any(ExamEntity.class));
        verify(applicationMapper, never()).mapEntityFromDomain(any(Application.class),
                any(UserEntity.class), any(SpecialtyEntity.class));
        verify(applicationRepository, never()).save(any(ApplicationEntity.class));
    }

    @Test
    public void specialtyApplyShouldThrowApplicationException() {
        expectedException.expect(ServiceRuntimeException.class);
        expectedException.expectMessage("User already has application!");
        when(specialtyMapper.mapDomainFromEntity(null)).thenReturn(specialty);
        when(applicationRepository.findByUser(null)).thenReturn(applicationEntity);
        studentService.specialtyApply(user.getEmail(), specialty.getName());

        verify(specialtyRepository).findBySpecialty(specialtyEntity.getSpecialty());
        verify(specialtyMapper).mapDomainFromEntity(null);
        verify(userRepository).findByEmail(user.getEmail());
        verify(applicationRepository).findByUser(null);
        verify(examRepository, never()).findByUser(any(UserEntity.class));
        verify(examMapper, never()).mapDomainFromEntity(any(ExamEntity.class));
        verify(applicationMapper, never()).mapEntityFromDomain(any(Application.class),
                any(UserEntity.class), any(SpecialtyEntity.class));
        verify(applicationRepository, never()).save(any(ApplicationEntity.class));
    }

    @Test
    public void specialtyApplyShouldApplyAndReturnSpecialtyName() {
        specialty.setRequirements(asList(Requirement.builder().subject("eng").mark(170).build(),
                Requirement.builder().subject("math").mark(170).build(),
                Requirement.builder().subject("physics").mark(170).build()));
        when(specialtyRepository.findBySpecialty(specialtyEntity.getSpecialty())).thenReturn(specialtyEntity);
        when(specialtyMapper.mapDomainFromEntity(specialtyEntity)).thenReturn(specialty);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(userEntity);
        when(applicationRepository.findByUser(userEntity)).thenReturn(null);
        when(examRepository.findByUser(userEntity)).thenReturn(examEntities);
        when(examMapper.mapDomainFromEntity(examEntities.get(0))).thenReturn(exams.get(0), exams.get(1), exams.get(2));
        when(applicationMapper.mapEntityFromDomain(application, userEntity, specialtyEntity)).thenReturn(applicationEntity);
        assertThat(studentService.specialtyApply(user.getEmail(), specialty.getName()),
                is(specialtyEntity.getSpecialty()));

        verify(specialtyRepository).findBySpecialty(specialtyEntity.getSpecialty());
        verify(specialtyMapper).mapDomainFromEntity(specialtyEntity);
        verify(userRepository).findByEmail(user.getEmail());
        verify(applicationRepository).findByUser(userEntity);
        verify(examRepository).findByUser(any(UserEntity.class));
        verify(examMapper, times(3)).mapDomainFromEntity(examEntities.get(0));
        verify(applicationMapper).mapEntityFromDomain(application,
                userEntity, specialtyEntity);
        verify(applicationRepository).save(applicationEntity);
    }
}
