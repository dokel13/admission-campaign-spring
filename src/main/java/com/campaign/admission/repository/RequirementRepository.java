package com.campaign.admission.repository;

import com.campaign.admission.entity.RequirementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequirementRepository extends JpaRepository<RequirementEntity, Integer> {
}
