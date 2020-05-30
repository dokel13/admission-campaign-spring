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
public class Application {

    private Integer id;

    @NotEmpty
    private User user;

    @NotEmpty
    private Specialty specialty;

    private Boolean enrollment;

    private Integer markSum;
}
