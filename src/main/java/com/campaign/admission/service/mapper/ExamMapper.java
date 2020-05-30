package com.campaign.admission.service.mapper;

import com.campaign.admission.domain.Exam;
import com.campaign.admission.entity.ExamEntity;
import com.campaign.admission.entity.SubjectEntity;
import com.campaign.admission.entity.UserEntity;
import com.campaign.admission.exception.DatabaseRuntimeException;
import com.campaign.admission.exception.ServiceRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Slf4j
@Component
public class ExamMapper {

    public Exam mapExamFromEntity(ExamEntity entity) {
        try {
            return isNull(entity) ? Exam.builder().build() : Exam.builder()
                    .subject(entity.getSubject().getSubject())
                    .mark(entity.getMark())
                    .build();
        } catch (Exception exception) {
            String message = "Exam mapping exception!";
            log.warn(message, exception);
            throw new DatabaseRuntimeException(exception, message);
        }
    }

    public ExamEntity mapEntityFromExam(Exam exam) {
        try {
            return isNull(exam) ? null : new ExamEntity(new SubjectEntity(exam.getSubject()),
                    new UserEntity(exam.getUser().getEmail()));
        } catch (Exception exception) {
            String message = "ExamEntity mapping exception!";
            log.warn(message, exception);
            throw new ServiceRuntimeException(exception, message);
        }
    }
}
