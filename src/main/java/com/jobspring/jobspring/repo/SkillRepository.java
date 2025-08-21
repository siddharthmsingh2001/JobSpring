package com.jobspring.jobspring.repo;

import com.jobspring.jobspring.entity.SeekerProfile;
import com.jobspring.jobspring.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    List<Skill> findBySeekerProfile(SeekerProfile seekerProfile);

}
