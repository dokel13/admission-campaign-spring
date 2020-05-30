package com.campaign.admission.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "email", nullable = false, unique = true, length = 45)
    private String email;

    @Column(name = "password", nullable = false, length = 45)
    private String password;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "surname", nullable = false, length = 45)
    private String surname;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role", referencedColumnName = "role_id", nullable = false)
    private RoleEntity role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private ApplicationEntity application;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExamEntity> exams;

    public UserEntity(String email, String password, String name, String surname, RoleEntity role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public UserEntity(String email) {
        this.email = email;
    }
}
