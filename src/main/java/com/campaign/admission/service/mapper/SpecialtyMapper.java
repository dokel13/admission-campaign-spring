package com.campaign.admission.service.mapper;

import com.campaign.admission.domain.Requirement;
import com.campaign.admission.domain.Specialty;
import com.campaign.admission.entity.SpecialtyEntity;
import com.campaign.admission.exception.DatabaseRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;

@Slf4j
@Component
public class SpecialtyMapper {

    public Specialty mapSpecialtyFromEntity(SpecialtyEntity entity) {
        try {
            if (isNull(entity)) {
                return Specialty.builder().build();
            }
            List<Requirement> requirements = entity.getRequirements().stream().map(r ->
                    Requirement.builder()
                            .subject(r.getSubject().getSubject())
                            .mark(r.getMark())
                            .build()).collect(toList());

            return of(Specialty.builder()
                    .name(entity.getSpecialty())
                    .maxStudentAmount(entity.getMaxStudentAmount())
                    .open(entity.isOpen())
                    .requirements(requirements)
                    .build()).orElse(null);
        } catch (Exception exception) {
            String message = "Specialty mapping exception!";
            log.warn(message, exception);
            throw new DatabaseRuntimeException(exception, message);
        }
    }
}
