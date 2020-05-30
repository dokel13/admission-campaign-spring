package com.campaign.admission.util;

import org.springframework.stereotype.Component;

@Component
public class PaginationUtils {

    public static Integer countPages(Integer pageSize, Integer examsCount) {
        int pagesCount;
        if (examsCount >= 0 && examsCount <= pageSize) {
            pagesCount = 1;
        } else {
            pagesCount = examsCount / pageSize;
            if (examsCount > 2 && (examsCount % pageSize) > 0) {
                pagesCount += 1;
            }
        }

        return pagesCount;
    }
}
