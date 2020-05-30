package com.campaign.admission.service;

import com.campaign.admission.domain.Exam;
import com.campaign.admission.domain.User;
import com.campaign.admission.entity.ExamEntity;
import com.campaign.admission.entity.SpecialtyEntity;
import com.campaign.admission.entity.SubjectEntity;
import com.campaign.admission.repository.ApplicationRepository;
import com.campaign.admission.repository.ExamRepository;
import com.campaign.admission.repository.SpecialtyRepository;
import com.campaign.admission.service.mapper.ExamMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.campaign.admission.util.AdmissionValidator.validateAdmissionOpen;
import static java.lang.Integer.parseInt;
import static java.lang.Math.min;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class AdminServiceImpl implements AdminService {

    private final ExamRepository examRepository;
    private final SpecialtyRepository specialtyRepository;
    private final ApplicationRepository applicationRepository;
    private final ExamMapper examMapper;

    @Override
    public List<String> getAllSubjects() {
        if (validateAdmissionOpen(specialtyRepository.findSpecialtiesOpens())) {
            return examRepository.findAllSubjects();
        }

        return null;
    }

    @Transactional
    @Override
    public void setAdmission(Boolean open) {
        if (open) {
            applicationRepository.setAllEnrollments(false);
        } else {
            List<SpecialtyEntity> specialtyEntities = specialtyRepository.findAll();
            for (SpecialtyEntity specialty : specialtyEntities) {
                applicationRepository.setEnrollmentsBySpecialties(true, specialty.getSpecialty(), specialty.getMaxStudentAmount());
            }
        }
        specialtyRepository.setAdmission(open);
    }

    @Override
    public List<Exam> getExamsPaginated(String subject, Integer page, Integer pageSize) {
        if (validateAdmissionOpen(specialtyRepository.findSpecialtiesOpens())) {
            PageRequest pageRequest = PageRequest.of(page, pageSize);
            Page<ExamEntity> exams = examRepository.findBySubject(new SubjectEntity(subject), pageRequest);
            if (exams.hasNext()) {
                return exams.stream().map(examMapper::mapExamFromEntity).collect(toList());
            }
        }

        return null;
    }

    @Override
    public Integer countExamsBySubject(String subject) {
        return examRepository.countBySubjectAndApplicationIsNull(subject);
    }

    @Transactional
    @Override
    public void saveMarks(String subject, String[] emails, String[] marks) {
        List<Exam> exams = range(0, min(emails.length, marks.length))
                .mapToObj(i -> Exam.builder()
                        .user(User.builder().email(emails[i]).build())
                        .mark(parseInt(of((marks[i])).filter(j -> !j.equals("")).orElse("0")))
                        .build()).collect(toList());
        for (Exam exam : exams) {
            examRepository.setMarks(exam.getMark(), exam.getUser().getEmail(), subject);
        }
    }

    @Override
    public Boolean checkAdmission() {
        return validateAdmissionOpen(specialtyRepository.findSpecialtiesOpens());
    }
}
