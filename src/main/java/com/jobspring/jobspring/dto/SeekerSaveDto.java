package com.jobspring.jobspring.dto;

import com.jobspring.jobspring.entity.SeekerApply;
import com.jobspring.jobspring.entity.SeekerSave;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SeekerSaveDto {

    private Long id;
    private Long jobPostActivityId;
    private Long accountId;
    private boolean saved = false;

    public SeekerSaveDto(SeekerSave seekerSave){
        this.id = seekerSave.getId();
        this.jobPostActivityId = seekerSave.getJobPostActivity().getId();
        this.accountId = seekerSave.getSeekerProfile().getAccountId();
        this.saved = true;
    }

    public SeekerSaveDto(){
        this.saved = false;
    }
}
