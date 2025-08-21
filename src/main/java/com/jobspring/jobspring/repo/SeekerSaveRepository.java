package com.jobspring.jobspring.repo;

import com.jobspring.jobspring.entity.JobPostActivity;
import com.jobspring.jobspring.entity.SeekerApply;
import com.jobspring.jobspring.entity.SeekerProfile;
import com.jobspring.jobspring.entity.SeekerSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeekerSaveRepository extends JpaRepository<SeekerSave, Long> {

    Optional<SeekerSave> findBySeekerProfileAndJobPostActivity(SeekerProfile seekerProfile, JobPostActivity jobPostActivity);

    List<SeekerSave> findBySeekerProfile(SeekerProfile seekerProfile);

    boolean existsBySeekerProfileAndJobPostActivity(SeekerProfile seekerProfile, JobPostActivity jobPostActivity);
}
