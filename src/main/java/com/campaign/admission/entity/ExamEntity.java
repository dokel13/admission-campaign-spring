package com.campaign.admission.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "exams")
public class ExamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_id", nullable = false)
    private int id;

    @Column(name = "mark")
    private Integer mark;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject", referencedColumnName = "subject_id", nullable = false)
    private SubjectEntity subject;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user", referencedColumnName = "user_id", nullable = false)
    private UserEntity user;

    public ExamEntity(SubjectEntity subject, UserEntity user) {
        this.subject = subject;
        this.user = user;
    }
}
