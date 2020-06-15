package com.campaign.admission.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "applications")
public class ApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id", nullable = false)
    private int id;

    @Column(name = "enrollment", nullable = false)
    private boolean enrollment;

    @Column(name = "mark_sum")
    private Integer markSum;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "specialty", referencedColumnName = "specialty_id", nullable = false)
    private SpecialtyEntity specialty;

    @OneToOne
    @JoinColumn(name = "user", referencedColumnName = "user_id", nullable = false)
    private UserEntity user;

    public ApplicationEntity(UserEntity user, SpecialtyEntity specialty, Integer markSum) {
        this.user = user;
        this.specialty = specialty;
        this.markSum = markSum;
    }
}
