package com.campaign.admission.util;

import org.springframework.stereotype.Component;

@Component
public final class PaginationUtils {

    public static Integer countPages(Integer pageSize, Integer entityCount) {
        int pagesCount;
        if (entityCount >= 0 && entityCount <= pageSize) {
            pagesCount = 1;
        } else {
            pagesCount = entityCount / pageSize;
            if (entityCount > 2 && (entityCount % pageSize) > 0) {
                pagesCount += 1;
            }
        }

        return pagesCount;
    }
}
