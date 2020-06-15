package com.campaign.admission.service.mapper;

import com.campaign.admission.domain.Role;
import com.campaign.admission.entity.RoleEntity;

import static com.campaign.admission.domain.Role.valueOf;

public class RoleMapper implements Mapper<Role, RoleEntity> {

    @Override
    public Role mapDomainFromEntity(RoleEntity entity) {
        Role role = valueOf(entity.getRole());
        role.setId(entity.getId());

        return role;
    }

    @Override
    public RoleEntity mapEntityFromDomain(Role role) {
        return null;
    }
}
