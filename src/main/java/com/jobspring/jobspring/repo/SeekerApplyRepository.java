package com.jobspring.jobspring.repo;

import com.jobspring.jobspring.entity.JobPostActivity;
import com.jobspring.jobspring.entity.SeekerApply;
import com.jobspring.jobspring.entity.SeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeekerApplyRepository extends JpaRepository<SeekerApply, Long> {

    Optional<SeekerApply> findBySeekerProfileAndJobPostActivity(SeekerProfile seekerProfile, JobPostActivity jobPostActivity);

    List<SeekerApply> findByJobPostActivity(JobPostActivity jobPostActivity);

    boolean existsBySeekerProfileAndJobPostActivity(SeekerProfile seekerProfile, JobPostActivity jobPostActivity);
}
