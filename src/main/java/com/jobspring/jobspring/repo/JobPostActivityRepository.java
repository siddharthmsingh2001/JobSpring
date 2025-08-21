package com.jobspring.jobspring.repo;

import com.jobspring.jobspring.entity.Account;
import com.jobspring.jobspring.entity.JobPostActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPostActivityRepository extends JpaRepository<JobPostActivity, Long> {

    List<JobPostActivity> findByAccount(Account account);

}
