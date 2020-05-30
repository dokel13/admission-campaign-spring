package com.campaign.admission.repository;

import com.campaign.admission.entity.SpecialtyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialtyRepository extends JpaRepository<SpecialtyEntity, Integer> {

    @Modifying
    @Query(value = "UPDATE specialties SET open = ?1", nativeQuery = true)
    void setAdmission(Boolean open);

    @Query(value = "SELECT open FROM specialties", nativeQuery = true)
    List<Boolean> findSpecialtiesOpens();

    SpecialtyEntity findBySpecialty(String specialty);

    @Query(value = "SELECT specialty FROM specialties", nativeQuery = true)
    List<String> findAllSpecialtiesNames();
}
