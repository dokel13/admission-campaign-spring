package com.campaign.admission.util;

import org.junit.Test;

import static com.campaign.admission.util.PaginationUtils.countPages;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PaginationUtilsTest {

    @Test
    public void countPagesShouldReturnPagesSum() {
        assertThat(countPages(5, 20), is(4));
    }
}
