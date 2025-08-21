package com.jobspring.jobspring.dto;

import com.jobspring.jobspring.entity.JobPostActivity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobPostActivityDto {

    private Long id;
    private String jobDescription;
    private String jobTitle;
    private String jobType;
    private LocalDateTime postedDate;
    private String remote;
    private String salary;
    private JobCompanyDto jobCompanyDto;
    private JobLocationDto jobLocationDto;
    private AccountDto accountDto;
    private Long accountId;

    public JobPostActivityDto(JobPostActivity jobPostActivity){
        this.id = jobPostActivity.getId();
        this.jobDescription = jobPostActivity.getJobDescription();
        this.jobTitle = jobPostActivity.getJobTitle();
        this.jobType = jobPostActivity.getJobType();
        this.postedDate = jobPostActivity.getPostedDate();
        this.remote = jobPostActivity.getRemote();
        this.salary = jobPostActivity.getSalary();
        this.jobCompanyDto = new JobCompanyDto(jobPostActivity.getJobCompany());
        this.jobLocationDto = new JobLocationDto(jobPostActivity.getJobLocation());
        this.accountDto = new AccountDto(jobPostActivity.getAccount());
        this.accountId = accountDto.getId();
    }
}
