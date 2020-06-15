package com.campaign.admission.repository;

import com.campaign.admission.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Integer> {

    SubjectEntity findBySubject(String subject);
}
