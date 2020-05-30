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
public class Requirement {

    private Integer id;

    @NotEmpty
    private String subject;

    @NotEmpty
    private Integer mark;
}
