package com.campaign.admission.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Exam {

    private Integer id;

    @NotEmpty
    private User user;

    @NotEmpty
    private String subject;

    private Integer mark;

    public Exam(User user, String subject, Integer mark) {
        this.user = user;
        this.subject = subject;
        this.mark = mark;
    }
}
