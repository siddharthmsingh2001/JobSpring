package com.jobspring.jobspring.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@Table(name = "skill")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "experience_level")
    private String experienceLevel;

    @Column(name = "name")
    private String name;

    @Column(name = "years_experience")
    private Byte yearExperience;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private SeekerProfile seekerProfile;

}
