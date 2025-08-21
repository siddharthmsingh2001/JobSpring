package com.jobspring.jobspring.repo;

import com.jobspring.jobspring.entity.Account;
import com.jobspring.jobspring.entity.SeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeekerProfileRepository extends JpaRepository<SeekerProfile, Long> {

    Optional<SeekerProfile> findByAccount(Account account);

    boolean existsByAccount(Account account);

}
