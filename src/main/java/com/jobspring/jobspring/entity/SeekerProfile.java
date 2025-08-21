package com.jobspring.jobspring.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import java.util.List;

@Entity
@Table(name = "seeker_profile")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class SeekerProfile implements Profile{

    @Id
    private Long accountId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "profile_photo")
    private String profilePhoto = "default-img.jpg";

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "employment_type")
    private String employmentType;

    @Column(name = "work_authorization")
    private String workAuthorization;

    @Column(name = "resume")
    private String resume;

    @OneToMany(targetEntity = Skill.class, cascade = CascadeType.ALL, mappedBy = "seekerProfile")
    private List<Skill> skills;
}
