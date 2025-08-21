package com.jobspring.jobspring.repo;

import com.jobspring.jobspring.entity.Account;
import com.jobspring.jobspring.entity.RecruiterProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile, Long> {

    Optional<RecruiterProfile> findByAccount(Account account);

    boolean existsByAccount(Account account);

}
