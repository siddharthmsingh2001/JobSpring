package com.jobspring.jobspring.dto;

import com.jobspring.jobspring.entity.Skill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillDto {

    private Long id;
    private String experienceLevel;
    private String name;
    private Byte yearExperience;
    private Long accountId;

    public SkillDto(Skill skill){
        this.id = skill.getId();
        this.experienceLevel = skill.getExperienceLevel();
        this.name = skill.getName();
        this.yearExperience = skill.getYearExperience();
        this.accountId = skill.getSeekerProfile().getAccountId();
    }
}
