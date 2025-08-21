package com.jobspring.jobspring.service;

import com.jobspring.jobspring.dto.SkillDto;
import com.jobspring.jobspring.entity.SeekerProfile;
import com.jobspring.jobspring.entity.Skill;
import com.jobspring.jobspring.exception.SkillNotFoundException;
import com.jobspring.jobspring.repo.SkillRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    @Transactional
    private Skill create(SkillDto skillDto, SeekerProfile seekerProfile){
        Skill skill = Skill.builder()
                .name(skillDto.getName())
                .experienceLevel(skillDto.getExperienceLevel())
                .yearExperience(skillDto.getYearExperience())
                .seekerProfile(seekerProfile)
                .build();
        return skillRepository.save(skill);
    }

    @Transactional
    public List<Skill> create(List<SkillDto> skillDtos, SeekerProfile seekerProfile){
        return skillDtos.stream()
                .map(skillDto -> create(skillDto, seekerProfile))
                .toList();
    }

    public List<Skill> read(SeekerProfile seekerProfile){
        return skillRepository.findBySeekerProfile(seekerProfile);
    }

    @Transactional
    private Skill update(SkillDto skillDto) throws SkillNotFoundException {
        Skill skill = skillRepository.findById(skillDto.getId()).orElseThrow(SkillNotFoundException::new);
        skill.setName(skillDto.getName());
        skill.setExperienceLevel(skillDto.getExperienceLevel());
        skill.setYearExperience(skillDto.getYearExperience());
        return skillRepository.save(skill);
    }
    
    public List<Skill> update(List<SkillDto> skillDtos, SeekerProfile seekerProfile) throws SkillNotFoundException{
        List<Skill> existingSkills = read(seekerProfile);
        Map<Long, Skill> existingById = existingSkills.stream()
                .collect(Collectors.toMap(Skill::getId, skill -> skill));
        List<Skill> updatedSkills = new ArrayList<>();

        for(SkillDto skillDto: skillDtos){
            if(skillDto.getId()!=null){
                if (existingById.containsKey(skillDto.getId())) {
                    Skill updated = update(skillDto);
                    updatedSkills.add(updated);
                    existingById.remove(skillDto.getId());
                } else {
                    throw new SkillNotFoundException();
                }
            } else{
                Skill createSkill = create(skillDto, seekerProfile);
                updatedSkills.add(createSkill);
            }
        }
        for (Skill leftover : existingById.values()) {
            delete(leftover.getId());
        }

        return updatedSkills;
    }

    @Transactional
    private void delete(Long id){
        skillRepository.deleteById(id);
    }
}
