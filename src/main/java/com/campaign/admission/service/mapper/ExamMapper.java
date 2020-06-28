package com.campaign.admission.service.mapper;

import com.campaign.admission.domain.Exam;
import com.campaign.admission.domain.User;
import com.campaign.admission.entity.ExamEntity;
import com.campaign.admission.entity.SubjectEntity;
import com.campaign.admission.entity.UserEntity;
import com.campaign.admission.exception.DatabaseRuntimeException;
import com.campaign.admission.exception.ServiceRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

@Slf4j
@Component
public class ExamMapper {

    public Exam mapDomainFromEntity(ExamEntity entity) {
        try {
            return isNull(entity) ? null : Exam.builder()
                    .user(User.builder()
                            .email(entity.getUser().getEmail())
                            .name(entity.getUser().getName())
                            .surname(entity.getUser().getSurname())
                            .build())
                    .subject(entity.getSubject().getSubject())
                    .mark(ofNullable(entity.getMark()).orElse(0))
                    .build();
        } catch (Exception exception) {
            String message = "Exam mapping exception!";
            log.debug(message, exception);
            throw new DatabaseRuntimeException(exception, message);
        }
    }

    public ExamEntity mapEntityFromDomain(SubjectEntity subject, UserEntity user) {
        try {
            return isNull(subject) || isNull(user) ? null : new ExamEntity(subject, user);
        } catch (Exception exception) {
            String message = "ExamEntity mapping exception!";
            log.debug(message, exception);
            throw new ServiceRuntimeException(exception, message);
        }
    }
}
