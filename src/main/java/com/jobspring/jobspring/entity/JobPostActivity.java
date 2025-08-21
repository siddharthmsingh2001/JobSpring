package com.jobspring.jobspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_post_activity")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class JobPostActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "job_description")
    private String jobDescription;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "job_type")
    private String jobType;

    @Column(name = "posted_date")
    private LocalDateTime postedDate;

    @Column(name = "remote")
    private String remote;

    @Column(name = "salary")
    private String salary;

    @ManyToOne
    @JoinColumn(name = "job_company_id", referencedColumnName = "id")
    private JobCompany jobCompany;

    @ManyToOne
    @JoinColumn(name = "job_location_id", referencedColumnName = "id")
    private JobLocation jobLocation;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

}
