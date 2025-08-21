package com.jobspring.jobspring.repo;

import com.jobspring.jobspring.entity.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {

    Optional<AccountType> findByName(String name);
}
