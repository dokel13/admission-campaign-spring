package com.campaign.admission.service;

import com.campaign.admission.domain.Exam;

import java.util.List;

public interface AdminService {

    List<String> getAllSubjects();

    void setAdmission(Boolean open);

    List<Exam> getExamsPaginated(String subject, Integer page, Integer pageSize);

    Integer countExamsBySubject(String subject);

    void saveMarks(String subject, String[] emails, String[] marks);

    Boolean checkAdmission();
}
