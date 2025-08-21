package com.jobspring.jobspring.service;

import com.jobspring.jobspring.entity.JobPostActivity;
import com.jobspring.jobspring.entity.SeekerApply;
import com.jobspring.jobspring.entity.SeekerProfile;
import com.jobspring.jobspring.exception.SeekerApplyNotFoundException;
import com.jobspring.jobspring.exception.SeekerSaveAlreadyExistsException;
import com.jobspring.jobspring.repo.SeekerApplyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeekerApplyService {

    private final SeekerApplyRepository seekerApplyRepository;

    @Transactional
    public SeekerApply create(SeekerProfile seekerProfile, JobPostActivity jobPostActivity) throws SeekerSaveAlreadyExistsException {
        if(seekerApplyRepository.existsBySeekerProfileAndJobPostActivity(seekerProfile,jobPostActivity)){
            throw new SeekerSaveAlreadyExistsException();
        }
        SeekerApply seekerApply = SeekerApply.builder()
                .applyDate(LocalDateTime.now())
                .seekerProfile(seekerProfile)
                .jobPostActivity(jobPostActivity)
                .build();
        return seekerApplyRepository.save(seekerApply);
    }

    public Optional<SeekerApply> readByAccountAndPost(SeekerProfile seekerProfile, JobPostActivity jobPostActivity){
        return seekerApplyRepository.findBySeekerProfileAndJobPostActivity(seekerProfile, jobPostActivity);
    }

    public List<SeekerApply> readByJobPostActivity(JobPostActivity jobPostActivity){
        return seekerApplyRepository.findByJobPostActivity(jobPostActivity);
    }

    @Transactional
    public void delete(Long id) throws SeekerApplyNotFoundException {
        SeekerApply seekerApply = seekerApplyRepository.findById(id).orElseThrow(SeekerApplyNotFoundException::new);
        seekerApplyRepository.delete(seekerApply);
    }

}
