package com.campaign.admission.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "requirements")
public class RequirementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requirement_id", nullable = false)
    private int requirementId;

    @Column(name = "mark", nullable = false)
    private int mark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialty", referencedColumnName = "specialty_id", nullable = false)
    private SpecialtyEntity specialty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject", referencedColumnName = "subject_id", nullable = false)
    private SubjectEntity subject;
}
