package com.campaign.admission.util;

import com.campaign.admission.domain.Exam;
import com.campaign.admission.domain.Requirement;
import com.campaign.admission.exception.AdmissionValidatorRuntimeException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdmissionValidator {

    public static Integer validateMarks(List<Exam> exams, List<Requirement> requirements) {
        int result = 0;
        for (Requirement requirement : requirements) {
            Exam exam = null;
            for (Exam e : exams) {
                if (requirement.getSubject().equals(e.getSubject())) {
                    exam = e;
                    break;
                }
            }
            if ((exam == null) || exam.getMark() < requirement.getMark()) {
                throw new AdmissionValidatorRuntimeException("Insufficient marks for specialty admission!");
            } else {
                result += exam.getMark();
            }
        }
        return result;
    }

    public static boolean validateAdmissionOpen(List<Boolean> opens) {
        boolean checkOpen = opens.get(0);
        for (Boolean b: opens) {
            if (checkOpen != b) {
                throw new AdmissionValidatorRuntimeException("Specialties' opens aren't synchronized!");
            }
            checkOpen = b;
        }

        return checkOpen;
    }
}
