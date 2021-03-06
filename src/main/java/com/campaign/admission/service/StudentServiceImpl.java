package com.campaign.admission.service;

import com.campaign.admission.domain.Application;
import com.campaign.admission.domain.Exam;
import com.campaign.admission.domain.Specialty;
import com.campaign.admission.entity.ApplicationEntity;
import com.campaign.admission.entity.SpecialtyEntity;
import com.campaign.admission.entity.UserEntity;
import com.campaign.admission.exception.ServiceRuntimeException;
import com.campaign.admission.repository.*;
import com.campaign.admission.service.mapper.ApplicationMapper;
import com.campaign.admission.service.mapper.ExamMapper;
import com.campaign.admission.service.mapper.SpecialtyMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.campaign.admission.util.AdmissionValidator.validateAdmissionOpen;
import static com.campaign.admission.util.AdmissionValidator.validateMarks;
import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.springframework.data.domain.PageRequest.of;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class StudentServiceImpl implements StudentService {

    private final ExamRepository examRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final SpecialtyRepository specialtyRepository;
    private final ApplicationRepository applicationRepository;
    private final SpecialtyMapper specialtyMapper;
    private final ExamMapper examMapper;
    private final ApplicationMapper applicationMapper;

    @Override
    public List<String> getUserFreeSubjects(String email) {
        if (validateAdmissionOpen(specialtyRepository.findSpecialtiesOpens())) {
            List<String> subjects = subjectRepository.findUserFreeSubjects(email);
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
                    .mapEntityFromDomain(subjectRepository.findBySubject(subject), userRepository.findByEmail(email))));
        }
    }

    @Override
    public List<String> getAllSpecialties() {
        return specialtyRepository.findAllSpecialtiesNames();
    }

    @Override
    public Specialty getSpecialty(String specialty) {
        return specialtyMapper.mapDomainFromEntity(specialtyRepository.findBySpecialty(specialty));
    }

    @Override
    public List<Exam> getResults(String email) {
        return examRepository.findByUser(userRepository.findByEmail(email))
                .stream().map(examMapper::mapDomainFromEntity)
                .collect(toList());
    }

    @Override
    public Application getApplication(String email) {
        if (validateAdmissionOpen(specialtyRepository.findSpecialtiesOpens())) {
            return null;
        }

        return applicationMapper.mapDomainFromEntity(applicationRepository
                .findByUser(userRepository.findByEmail(email)));
    }

    @Override
    public Integer countApplicationsBySpecialty(String specialty) {
        return applicationRepository.countBySpecialty(specialtyRepository.findBySpecialty(specialty));
    }

    @Override
    public List<Application> getApplicationsPaginated(String specialty, Integer page, Integer pageSize) {
        PageRequest pageRequest = of(page, pageSize);
        PageImpl<ApplicationEntity> applications = applicationRepository.findBySpecialtyOrderByMarkSumDesc(specialtyRepository
                .findBySpecialty(specialty), pageRequest);
        if (applications.hasContent()) {
            return applications.stream().map(applicationMapper::mapDomainFromEntity).collect(toList());
        }

        return null;
    }

    @Transactional
    @Override
    public String specialtyApply(String email, String specialtyName) {
        SpecialtyEntity specialtyEntity = specialtyRepository.findBySpecialty(specialtyName);
        Specialty specialty = specialtyMapper.mapDomainFromEntity(specialtyEntity);
        UserEntity userEntity = userRepository.findByEmail(email);
        int markSum = applicationValidator(userEntity, specialty);
        applicationRepository.save(applicationMapper.mapEntityFromDomain(Application.builder()
                .markSum(markSum)
                .build(), userEntity, specialtyEntity));

        return specialtyName;
    }

    private Integer applicationValidator(UserEntity userEntity, Specialty specialty) {
        if (!specialty.getOpen()) {
            throw new ServiceRuntimeException("Admission is closed!");
        }
        ofNullable(applicationRepository.findByUser(userEntity)).ifPresent(app -> {
            throw new ServiceRuntimeException("User already has application!");
        });
        List<Exam> exams = examRepository.findByUser(userEntity)
                .stream().map(examMapper::mapDomainFromEntity).collect(toList());

        return validateMarks(exams, specialty.getRequirements());
    }
}
