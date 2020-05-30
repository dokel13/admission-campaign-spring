package com.campaign.admission.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Specialty {

    private Integer id;

    @NotEmpty
    private String name;

    @NotEmpty
    private Integer maxStudentAmount;

    @NotEmpty
    private Boolean open;

    @NotEmpty
    private List<Requirement> requirements;
}
