package com.jobspring.jobspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "seeker_save")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class SeekerSave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private SeekerProfile seekerProfile;

    @ManyToOne
    @JoinColumn(name = "job_post_activity_id", referencedColumnName = "id")
    private JobPostActivity jobPostActivity;

}
