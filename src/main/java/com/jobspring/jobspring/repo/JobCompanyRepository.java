package com.jobspring.jobspring.repo;

import com.jobspring.jobspring.entity.JobCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobCompanyRepository extends JpaRepository<JobCompany, Long> {

    Optional<JobCompany> findByName(String name);

}
