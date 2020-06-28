package com.campaign.admission.repository;

import com.campaign.admission.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Integer> {

    SubjectEntity findBySubject(String subject);

    @Query(value = "SELECT s.subject FROM SubjectEntity s")
    List<String> findAllSubjects();

    @Query(value = "SELECT subject FROM subjects WHERE subject_id NOT IN (SELECT subject FROM exams WHERE user = "
            + "(SELECT user_id FROM users WHERE email = :email))", nativeQuery = true)
    List<String> findUserFreeSubjects(@Param("email") String email);
}
