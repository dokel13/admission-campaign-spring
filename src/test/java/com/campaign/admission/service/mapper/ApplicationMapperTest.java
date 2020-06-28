package com.campaign.admission.service.mapper;

import com.campaign.admission.domain.Application;
import com.campaign.admission.domain.Specialty;
import com.campaign.admission.domain.User;
import com.campaign.admission.entity.ApplicationEntity;
import com.campaign.admission.entity.RoleEntity;
import com.campaign.admission.entity.SpecialtyEntity;
import com.campaign.admission.entity.UserEntity;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

public class ApplicationMapperTest {

    private final ApplicationMapper mapper = new ApplicationMapper();

    private final Application domain = Application.builder()
            .user(User.builder()
                    .email("email")
                    .name("name")
                    .surname("surname")
                    .build())
            .specialty(Specialty.builder()
                    .name("telecommunications")
                    .build())
            .markSum(500)
            .enrollment(false)
            .build();

    private final UserEntity userEntity = new UserEntity(
            "email",
            "password",
            "name",
            "surname",
            new RoleEntity());

    private final SpecialtyEntity specialtyEntity = new SpecialtyEntity("telecommunications");

    private final ApplicationEntity entity = new ApplicationEntity(userEntity,
            specialtyEntity, 500);


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
        assertThat(mapper.mapEntityFromDomain(domain, userEntity, specialtyEntity), is(entity));
    }

    @Test
    public void mapEntityFromDomainShouldReturnNull() {
        assertThat(mapper.mapEntityFromDomain(domain, userEntity, null), is(nullValue()));
    }
}
