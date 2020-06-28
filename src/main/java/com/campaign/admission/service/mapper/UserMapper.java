package com.campaign.admission.service.mapper;

import com.campaign.admission.domain.Role;
import com.campaign.admission.domain.User;
import com.campaign.admission.entity.RoleEntity;
import com.campaign.admission.entity.UserEntity;
import com.campaign.admission.exception.DatabaseRuntimeException;
import com.campaign.admission.exception.ServiceRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Slf4j
@Component
public class UserMapper {

    public User mapDomainFromEntity(UserEntity entity) {
        try {
            return isNull(entity) ? null : User.builder()
                    .role(Role.valueOf(entity
                            .getRole()
                            .getRole()))
                    .email(entity.getEmail())
                    .password(entity.getPassword())
                    .name(entity.getName())
                    .surname(entity.getSurname())
                    .build();
        } catch (Exception exception) {
            String message = "User mapping exception!";
            log.debug(message, exception);
            throw new DatabaseRuntimeException(exception, message);
        }
    }

    public UserEntity mapEntityFromDomain(User user, RoleEntity role) {
        try {
            return isNull(user) || isNull(role) ? null : new UserEntity(user.getEmail(),
                    user.getPassword(),
                    user.getName(),
                    user.getSurname(),
                    role);
        } catch (Exception exception) {
            String message = "UserEntity mapping exception!";
            log.debug(message, exception);
            throw new ServiceRuntimeException(exception, message);
        }
    }
}
