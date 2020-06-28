package com.campaign.admission.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "specialties")
public class SpecialtyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specialty_id", nullable = false)
    private int id;

    @Column(name = "specialty", nullable = false, length = 45)
    private String specialty;

    @Column(name = "max_student_amount", nullable = false)
    private int maxStudentAmount;

    @Column(name = "open", nullable = false)
    private boolean open;

    @OneToMany(mappedBy = "specialty", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<RequirementEntity> requirements;

    @OneToMany(mappedBy = "specialty", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApplicationEntity> applications;

    public SpecialtyEntity(String specialty) {
        this.specialty = specialty;
    }

    public SpecialtyEntity(String specialty, int maxStudentAmount) {
        this.specialty = specialty;
        this.maxStudentAmount = maxStudentAmount;
    }
}
