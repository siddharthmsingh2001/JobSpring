package com.jobspring.jobspring.dto;

import com.jobspring.jobspring.entity.SeekerApply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class SeekerApplyDto {

    private Long id;
    private LocalDateTime applyDate;
    private String coverLetter;
    private Long jobPostActivityId;
    private Long accountId;
    private boolean applied = false; // The added field that will help us determine it in the form

    public SeekerApplyDto(SeekerApply seekerApply){
        this.id = seekerApply.getId();
        this.applyDate = seekerApply.getApplyDate();
        this.coverLetter = seekerApply.getCoverLetter();
        this.jobPostActivityId = seekerApply.getJobPostActivity().getId();
        this.accountId = seekerApply.getSeekerProfile().getAccountId();
        this.applied = true;
    }

    public SeekerApplyDto(){
        this.applied = false;
    }
}
