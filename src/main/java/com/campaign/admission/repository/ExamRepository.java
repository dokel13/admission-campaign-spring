package com.campaign.admission.repository;

import com.campaign.admission.entity.ExamEntity;
import com.campaign.admission.entity.UserEntity;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<ExamEntity, Integer> {

    @Query(value = "SELECT COUNT(exam_id) AS count FROM exams WHERE subject = (SELECT subject_id FROM subjects WHERE " +
            "subject = :subject) AND user NOT IN (SELECT user FROM applications WHERE user = user)", nativeQuery = true)
    Integer countBySubjectAndApplicationIsNull(@Param("subject") String subject);

    @Modifying
    @Query(value = "UPDATE exams SET mark = :mark WHERE user = (SELECT user_id FROM users WHERE email = :email) "
            + "AND subject = (SELECT subject_id FROM subjects WHERE subject = :subject)", nativeQuery = true)
    void setMarks(@Param("mark") Integer mark, @Param("email") String email, @Param("subject") String subject);

    List<ExamEntity> findByUser(UserEntity user);

    @Query(value = "SELECT * FROM exams JOIN users ON user = user_id WHERE subject = (SELECT subject_id " +
            "FROM subjects WHERE subject = :subject) AND user_id NOT IN (SELECT user FROM applications WHERE user = user_id)",
            nativeQuery = true)
    PageImpl<ExamEntity> findBySubject(@Param("subject") String subject, Pageable pageable);
}
