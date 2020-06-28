package com.campaign.admission.util;

import com.campaign.admission.domain.Exam;
import com.campaign.admission.domain.Requirement;
import com.campaign.admission.exception.AdmissionValidatorRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
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
                String message = "Insufficient marks for specialty admission!";
                log.info(message);
                throw new AdmissionValidatorRuntimeException(message);
            } else {
                result += exam.getMark();
            }
        }

        return result;
    }

    public static boolean validateAdmissionOpen(List<Boolean> opens) {
        boolean checkOpen = opens.get(0);
        for (Boolean b : opens) {
            if (checkOpen != b) {
                String message = "Specialties' opens aren't synchronized!";
                log.warn(message);
                throw new AdmissionValidatorRuntimeException(message);
            }
            checkOpen = b;
        }

        return checkOpen;
    }
}
