package com.campaign.admission.repository;

import com.campaign.admission.entity.ApplicationEntity;
import com.campaign.admission.entity.SpecialtyEntity;
import com.campaign.admission.entity.UserEntity;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Integer> {

    @Modifying
    @Query(value = "UPDATE ApplicationEntity a SET a.enrollment = :enrollment")
    void setAllEnrollments(@Param("enrollment") boolean enrollment);

    @Modifying
    @Query(value = "UPDATE applications a SET enrollment = :enrollment WHERE specialty = (SELECT specialty_id FROM specialties "
            + "WHERE specialty = :specialty) ORDER BY mark_sum DESC LIMIT :maxStudentAmount", nativeQuery = true)
    void setEnrollmentsBySpecialties(@Param("enrollment") boolean enrollment, @Param("specialty") String specialty,
                                     @Param("maxStudentAmount") int maxStudentAmount);

    ApplicationEntity findByUser(UserEntity user);

    Integer countBySpecialty(SpecialtyEntity specialty);

    PageImpl<ApplicationEntity> findBySpecialtyOrderByMarkSumDesc(SpecialtyEntity specialty, Pageable page);
}
