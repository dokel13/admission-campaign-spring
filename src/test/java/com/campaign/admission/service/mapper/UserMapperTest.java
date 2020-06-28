package com.campaign.admission.service.mapper;

import com.campaign.admission.domain.Role;
import com.campaign.admission.domain.User;
import com.campaign.admission.entity.RoleEntity;
import com.campaign.admission.entity.UserEntity;
import org.junit.Test;

import static com.campaign.admission.domain.User.builder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

public class UserMapperTest {

    private final UserMapper mapper = new UserMapper();

    private final User domain = builder()
            .role(Role.STUDENT)
            .email("email")
            .password("password")
            .name("name")
            .surname("surname")
            .build();

    private final RoleEntity roleEntity = new RoleEntity(1, Role.STUDENT.toString());

    private final UserEntity entity = new UserEntity(
            "email",
            "password",
            "name",
            "surname",
            roleEntity);

    @Test
    public void mapDomainFromEntityShouldReturnDomain() {
        assertThat(mapper.mapDomainFromEntity(entity), is(domain));
    }

    @Test
    public void mapDomainFromEntityShouldReturnNull() {
        assertThat(mapper.mapDomainFromEntity(null), is(nullValue()));
    }

    @Test
    public void mapEntityFromDomainShouldReturnEntity() {
        assertThat(mapper.mapEntityFromDomain(domain, roleEntity), is(entity));
    }

    @Test
    public void mapEntityFromDomainShouldReturnNull() {
        assertThat(mapper.mapEntityFromDomain(null, roleEntity), is(nullValue()));
    }
}
