package com.campaign.admission.service.mapper;

import com.campaign.admission.domain.Exam;
import com.campaign.admission.domain.User;
import com.campaign.admission.entity.ExamEntity;
import com.campaign.admission.entity.RoleEntity;
import com.campaign.admission.entity.SubjectEntity;
import com.campaign.admission.entity.UserEntity;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

public class ExamMapperTest {

    private final ExamMapper mapper = new ExamMapper();

    private final Exam domain = Exam.builder()
            .user(User.builder()
                    .email("email")
                    .name("name")
                    .surname("surname")
                    .build())
            .subject("math")
            .mark(0)
            .build();

    private final SubjectEntity subjectEntity = new SubjectEntity("math");

    private final UserEntity userEntity = new UserEntity(
            "email",
            "password",
            "name",
            "surname",
            new RoleEntity());

    private final ExamEntity entity = new ExamEntity(subjectEntity, userEntity);

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
        assertThat(mapper.mapEntityFromDomain(subjectEntity, userEntity), is(entity));
    }

    @Test
    public void mapEntityFromDomainShouldReturnNull() {
        assertThat(mapper.mapEntityFromDomain(null, userEntity), is(nullValue()));
    }
}
