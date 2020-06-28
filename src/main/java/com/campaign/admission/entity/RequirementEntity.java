package com.campaign.admission.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "requirements")
public class RequirementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requirement_id", nullable = false)
    private int id;

    @Column(name = "mark", nullable = false)
    private int mark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialty", referencedColumnName = "specialty_id", nullable = false)
    private SpecialtyEntity specialty;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject", referencedColumnName = "subject_id", nullable = false)
    private SubjectEntity subject;
}
