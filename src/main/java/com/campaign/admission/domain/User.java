package com.campaign.admission.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;

    private Role role;

    @Pattern(regexp = "^\\w+@\\D+\\.\\D+$", message = "Wrong email!")
    @NotEmpty
    private String email;

    private String password;

    private String name;

    private String surname;
}
