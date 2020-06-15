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
public class UserMapper implements Mapper<User, UserEntity> {

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
            log.warn(message, exception);
            throw new DatabaseRuntimeException(exception, message);
        }
    }

    @Override
    public UserEntity mapEntityFromDomain(User user) {
        try {
            return isNull(user) ? null : new UserEntity(user.getEmail(),
                    user.getPassword(),
                    user.getName(),
                    user.getSurname(),
                    new RoleEntity(user.getRole().getId(), user.getRole().toString()));
        } catch (Exception exception) {
            String message = "UserEntity mapping exception!";
            log.warn(message, exception);
            throw new ServiceRuntimeException(exception, message);
        }
    }
}
