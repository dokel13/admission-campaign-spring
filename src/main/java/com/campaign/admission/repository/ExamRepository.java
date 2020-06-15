package com.campaign.admission.repository;

import com.campaign.admission.entity.ExamEntity;
import com.campaign.admission.entity.SubjectEntity;
import com.campaign.admission.entity.UserEntity;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<ExamEntity, Integer> {

    @Query(value = "SELECT subject FROM subjects", nativeQuery = true)
    List<String> findAllSubjects();

    @Query(value = "SELECT COUNT(exam_id) AS count FROM exams WHERE subject = (SELECT subject_id FROM subjects WHERE subject = ?1) "
            + "AND user NOT IN (SELECT user FROM applications WHERE user = user)", nativeQuery = true)
    Integer countBySubjectAndApplicationIsNull(String subject);

    @Modifying
    @Query(value = "UPDATE exams SET mark = ?1 WHERE user = (SELECT user_id FROM users WHERE email = ?2) "
            + "AND subject = (SELECT subject_id FROM subjects WHERE subject = ?3)", nativeQuery = true)
    void setMarks(Integer mark, String email, String subject);

    @Query(value = "SELECT subject FROM subjects WHERE subject_id NOT IN (SELECT subject FROM exams WHERE user = "
            + "(SELECT user_id FROM users WHERE email = ?1))", nativeQuery = true)
    List<String> findUserFreeSubjects(String email);

    List<ExamEntity> findByUser(UserEntity user);

    PageImpl<ExamEntity> findBySubject(SubjectEntity subject, Pageable page);
}
