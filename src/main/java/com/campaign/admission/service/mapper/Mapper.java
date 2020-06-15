package com.campaign.admission.service.mapper;

public interface Mapper<Domain, Entity> {

    Domain mapDomainFromEntity(Entity entity);

    Entity mapEntityFromDomain(Domain domain);
}
