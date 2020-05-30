package com.campaign.admission.service;

import com.campaign.admission.domain.Application;
import com.campaign.admission.domain.Exam;
import com.campaign.admission.domain.Specialty;

import java.util.List;

public interface StudentService {

    List<String> getUserFreeSubjects(String email);

    void saveExamSubjects(String[] subjects, String email);

    List<String> getAllSpecialties();

    Specialty getSpecialty(String specialty);

    List<Exam> getResults(String email);

    Application getApplication(String email);

    Integer countApplicationsBySpecialty(String specialty);

    List<Application> getApplicationsPaginated(String specialty, Integer page, Integer pageSize);

    String specialtyApply(String email, String specialty);
}
