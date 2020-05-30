package com.campaign.admission.service;

import com.campaign.admission.domain.Application;
import com.campaign.admission.domain.Exam;
import com.campaign.admission.domain.Specialty;
import com.campaign.admission.domain.User;
import com.campaign.admission.entity.ApplicationEntity;
import com.campaign.admission.entity.SpecialtyEntity;
import com.campaign.admission.entity.UserEntity;
import com.campaign.admission.exception.ServiceRuntimeException;
import com.campaign.admission.repository.ApplicationRepository;
import com.campaign.admission.repository.ExamRepository;
import com.campaign.admission.repository.SpecialtyRepository;
import com.campaign.admission.service.mapper.ApplicationMapper;
import com.campaign.admission.service.mapper.ExamMapper;
import com.campaign.admission.service.mapper.SpecialtyMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.campaign.admission.util.AdmissionValidator.validateAdmissionOpen;
import static com.campaign.admission.util.AdmissionValidator.validateMarks;
import static java.util.Arrays.stream;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class StudentServiceImpl implements StudentService {

    private final ExamRepository examRepository;
    private final SpecialtyRepository specialtyRepository;
    private final ApplicationRepository applicationRepository;
    private final SpecialtyMapper specialtyMapper;
    private final ExamMapper examMapper;
    private final ApplicationMapper applicationMapper;

    @Override
    public List<String> getUserFreeSubjects(String email) {
        if (validateAdmissionOpen(specialtyRepository.findSpecialtiesOpens())) {
            List<String> subjects = examRepository.findUserFreeSubjects(email);
            if (subjects.size() == 0) {
                throw new ServiceRuntimeException("Finding subjects exception! No user free subjects are found!");
            } else {

                return subjects;
            }
        } else {
            throw new ServiceRuntimeException("Admission is closed!");
        }
    }

    @Transactional
    @Override
    public void saveExamSubjects(String[] subjects, String email) {
        if (subjects != null) {
            stream(subjects).forEach(subject -> examRepository.save(examMapper
                    .mapEntityFromExam(Exam.builder().subject(subject)
                            .user(User.builder().email(email).build()).build())));
        }
    }

    @Override
    public List<String> getAllSpecialties() {
        return specialtyRepository.findAllSpecialtiesNames();
    }

    @Override
    public Specialty getSpecialty(String specialty) {
        return specialtyMapper.mapSpecialtyFromEntity(specialtyRepository.findBySpecialty(specialty));
    }

    @Override
    public List<Exam> getResults(String email) {
        return examRepository.findByUser(new UserEntity(email))
                .stream().map(examMapper::mapExamFromEntity)
                .collect(toList());
    }

    @Override
    public Application getApplication(String email) {
        if (validateAdmissionOpen(specialtyRepository.findSpecialtiesOpens())) {
            return null;
        }

        return applicationMapper.mapApplicationFromEntity(applicationRepository
                .findByUser(new UserEntity(email)));
    }

    @Override
    public Integer countApplicationsBySpecialty(String specialty) {
        return applicationRepository.countBySpecialty(new SpecialtyEntity(specialty));
    }

    @Override
    public List<Application> getApplicationsPaginated(String specialty, Integer page, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<ApplicationEntity> applications = applicationRepository.findBySpecialty(new SpecialtyEntity(specialty), pageRequest);
        if (applications.hasNext()) {
            return applications.stream().map(applicationMapper::mapApplicationFromEntity).collect(toList());
        }

        return null;
    }

    @Transactional
    @Override
    public String specialtyApply(String email, String specialtyName) {
        Specialty specialty = specialtyMapper.mapSpecialtyFromEntity(specialtyRepository
                .findBySpecialty(specialtyName));
        int markSum = applicationValidator(email, specialty);
        User user = User.builder()
                .email(email)
                .build();
        applicationRepository.save(applicationMapper.mapEntityFromApplication(Application.builder()
                .user(user)
                .specialty(specialty)
                .markSum(markSum)
                .build()));

        return specialtyName;
    }

    private Integer applicationValidator(String email, Specialty specialty) {
        if (!specialty.getOpen()) {
            throw new ServiceRuntimeException("Admission is closed!");
        }
        of(applicationRepository.findByUser(new UserEntity(email))).ifPresent(app -> {
            throw new ServiceRuntimeException("User already has application!");
        });
        List<Exam> exams = examRepository.findByUser(new UserEntity(email))
                .stream().map(examMapper::mapExamFromEntity).collect(toList());

        return validateMarks(exams, specialty.getRequirements());
    }
}
