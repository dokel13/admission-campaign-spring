package com.campaign.admission.repository;

import com.campaign.admission.entity.ApplicationEntity;
import com.campaign.admission.entity.SpecialtyEntity;
import com.campaign.admission.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Integer> {

    @Modifying
    @Query(value = "UPDATE applications SET enrollment = ?1", nativeQuery = true)
    void setAllEnrollments(boolean b);

    @Modifying
    @Query(value = "UPDATE applications SET enrollment = ?1 WHERE specialty = (SELECT specialty_id FROM specialties WHERE specialty = ?2) "
            + "ORDER BY mark_sum DESC LIMIT ?3", nativeQuery = true)
    void setEnrollmentsBySpecialties(boolean b, String specialty, int maxStudentAmount);

    ApplicationEntity findByUser(UserEntity user);

    Integer countBySpecialty(SpecialtyEntity specialty);

    Page<ApplicationEntity> findBySpecialty(SpecialtyEntity specialty, Pageable page);
}
