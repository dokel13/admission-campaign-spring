package com.campaign.admission.service.mapper;

import com.campaign.admission.domain.Application;
import com.campaign.admission.domain.Specialty;
import com.campaign.admission.domain.User;
import com.campaign.admission.entity.ApplicationEntity;
import com.campaign.admission.entity.SpecialtyEntity;
import com.campaign.admission.entity.UserEntity;
import com.campaign.admission.exception.DatabaseRuntimeException;
import com.campaign.admission.exception.ServiceRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Slf4j
@Component
public class ApplicationMapper {

    public Application mapApplicationFromEntity(ApplicationEntity entity) {
        try {
            return isNull(entity) ? null : Application.builder()
                    .user(User.builder()
                            .email(entity.getUser().getEmail())
                            .name(entity.getUser().getName())
                            .surname(entity.getUser().getSurname())
                            .build())
                    .specialty(Specialty.builder()
                            .name(entity.getSpecialty().getSpecialty())
                            .build())
                    .markSum(entity.getMarkSum())
                    .enrollment(entity.isEnrollment())
                    .build();
        } catch (Exception exception) {
            String message = "Application mapping exception!";
            log.warn(message, exception);
            throw new DatabaseRuntimeException(exception, message);
        }
    }

    public ApplicationEntity mapEntityFromApplication(Application application, UserEntity user, SpecialtyEntity specialty) {
        try {
            return isNull(application) ? null :
                    new ApplicationEntity(user, specialty,
                            application.getMarkSum());
        } catch (Exception exception) {
            String message = "ApplicationEntity mapping exception!";
            log.warn(message, exception);
            throw new ServiceRuntimeException(exception, message);
        }
    }
}
