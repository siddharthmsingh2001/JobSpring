package com.jobspring.jobspring.repo;

import com.jobspring.jobspring.entity.JobLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobLocationRepository extends JpaRepository<JobLocation, Long> {



}
