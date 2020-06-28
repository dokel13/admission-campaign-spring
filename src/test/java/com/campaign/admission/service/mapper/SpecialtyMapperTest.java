package com.campaign.admission.service.mapper;

import com.campaign.admission.domain.Requirement;
import com.campaign.admission.domain.Specialty;
import com.campaign.admission.entity.RequirementEntity;
import com.campaign.admission.entity.SpecialtyEntity;
import com.campaign.admission.entity.SubjectEntity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

public class SpecialtyMapperTest {

    private final SpecialtyMapper mapper = new SpecialtyMapper();

    private final List<Requirement> requirements = new ArrayList<Requirement>() {{
        add(Requirement.builder().subject("math").mark(150).build());
        add(Requirement.builder().subject("physics").mark(160).build());
        add(Requirement.builder().subject("ukr").mark(170).build());
    }};

    private final Specialty domain = Specialty.builder()
            .name("linguistics")
            .maxStudentAmount(15)
            .open(true)
            .requirements(requirements)
            .build();

    private final List<RequirementEntity> requirementEntities = new ArrayList<RequirementEntity>() {{
        add(new RequirementEntity(1, 150, new SpecialtyEntity(), new SubjectEntity("math")));
        add(new RequirementEntity(2, 160, new SpecialtyEntity(), new SubjectEntity("physics")));
        add(new RequirementEntity(5, 170, new SpecialtyEntity(), new SubjectEntity("ukr")));
    }};

    private final SpecialtyEntity entity = new SpecialtyEntity(1,
            "linguistics",
            15,
            true,
            requirementEntities,
            null);

    @Test
    public void mapDomainFromEntityShouldReturnDomain() {
        assertThat(mapper.mapDomainFromEntity(entity), is(domain));
    }

    @Test
    public void mapDomainFromEntityShouldReturnNull() {
        assertThat(mapper.mapDomainFromEntity(null), is(nullValue()));
    }
}
