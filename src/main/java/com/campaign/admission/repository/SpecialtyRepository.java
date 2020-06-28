package com.campaign.admission.repository;

import com.campaign.admission.entity.SpecialtyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialtyRepository extends JpaRepository<SpecialtyEntity, Integer> {

    @Modifying
    @Query(value = "UPDATE SpecialtyEntity s SET s.open = :open")
    void setAdmission(@Param("open") Boolean open);

    @Query(value = "SELECT s.open FROM SpecialtyEntity s")
    List<Boolean> findSpecialtiesOpens();

    SpecialtyEntity findBySpecialty(String specialty);

    @Query(value = "SELECT s.specialty FROM SpecialtyEntity s")
    List<String> findAllSpecialtiesNames();
}
