package com.jobspring.jobspring.service;

import com.jobspring.jobspring.dto.SeekerSaveDto;
import com.jobspring.jobspring.entity.JobPostActivity;
import com.jobspring.jobspring.entity.SeekerProfile;
import com.jobspring.jobspring.entity.SeekerSave;
import com.jobspring.jobspring.exception.SeekerSaveAlreadyExistsException;
import com.jobspring.jobspring.exception.SeekerSaveNotFoundException;
import com.jobspring.jobspring.repo.SeekerSaveRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeekerSaveService {

    private final SeekerSaveRepository seekerSaveRepository;

    @Transactional
    public SeekerSave create(SeekerProfile seekerProfile, JobPostActivity jobPostActivity) throws SeekerSaveAlreadyExistsException {
        if(seekerSaveRepository.existsBySeekerProfileAndJobPostActivity(seekerProfile, jobPostActivity)){
            throw new SeekerSaveAlreadyExistsException();
        }
        SeekerSave seekerSave = SeekerSave.builder()
                .seekerProfile(seekerProfile)
                .jobPostActivity(jobPostActivity)
                .build();
        return seekerSaveRepository.save(seekerSave);
    }

    public Optional<SeekerSave> readByAccountAndPost(SeekerProfile seekerProfile, JobPostActivity jobPostActivity){
        return seekerSaveRepository.findBySeekerProfileAndJobPostActivity(seekerProfile,jobPostActivity);
    }

    public List<SeekerSave> readBySeekerProfile(SeekerProfile seekerProfile){
        return seekerSaveRepository.findBySeekerProfile(seekerProfile);
    }

    @Transactional
    public void delete(Long id) throws SeekerSaveNotFoundException {
        SeekerSave seekerSave = seekerSaveRepository.findById(id).orElseThrow(SeekerSaveNotFoundException::new);
        seekerSaveRepository.delete(seekerSave);
    }

}
