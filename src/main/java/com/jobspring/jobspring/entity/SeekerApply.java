package com.jobspring.jobspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Entity
@Table(name = "seeker_apply")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class SeekerApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "apply_date")
    private LocalDateTime applyDate;

    @Column(name = "cover_letter")
    private String coverLetter;

    @ManyToOne
    @JoinColumn(name = "job_post_activity_id", referencedColumnName = "id")
    private JobPostActivity jobPostActivity;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private SeekerProfile seekerProfile;

}
