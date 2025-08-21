package com.jobspring.jobspring.service;

import com.jobspring.jobspring.dto.JobPostActivityDto;
import com.jobspring.jobspring.entity.Account;
import com.jobspring.jobspring.entity.JobCompany;
import com.jobspring.jobspring.entity.JobLocation;
import com.jobspring.jobspring.entity.JobPostActivity;
import com.jobspring.jobspring.exception.JobPostActivityNotFoundException;
import com.jobspring.jobspring.repo.JobPostActivityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobPostActivityService {

    private final JobPostActivityRepository jobPostActivityRepository;

    @Transactional
    public JobPostActivity create(JobPostActivityDto jobPostActivityDto, Account account, JobLocation jobLocation, JobCompany jobCompany){
        JobPostActivity jobPostActivity = JobPostActivity.builder()
                .jobDescription(jobPostActivityDto.getJobDescription())
                .jobTitle(jobPostActivityDto.getJobTitle())
                .jobType(jobPostActivityDto.getJobType())
                .postedDate(LocalDateTime.now())
                .remote(jobPostActivityDto.getRemote())
                .salary(jobPostActivityDto.getSalary())
                .jobCompany(jobCompany)
                .jobLocation(jobLocation)
                .account(account)
                .build();
        return jobPostActivityRepository.save(jobPostActivity);
    }

    public List<JobPostActivity> findAll(){
        return jobPostActivityRepository.findAll();
    }

    public List<JobPostActivity> findByAccount(Account account){
        return jobPostActivityRepository.findByAccount(account);
    }

    public JobPostActivity findById(Long id) throws JobPostActivityNotFoundException {
        return jobPostActivityRepository.findById(id).orElseThrow(JobPostActivityNotFoundException::new);
    }

    public void deleteById(Long id) throws JobPostActivityNotFoundException {
        JobPostActivity jobPostActivity = jobPostActivityRepository.findById(id).orElseThrow(JobPostActivityNotFoundException::new);
        jobPostActivityRepository.delete(jobPostActivity);
    }

    public JobPostActivity update(JobPostActivityDto jobPostActivityDto, Account account, JobLocation jobLocation, JobCompany jobCompany) throws JobPostActivityNotFoundException {
        JobPostActivity jobPostActivity = jobPostActivityRepository.findById(jobPostActivityDto.getId()).orElseThrow(JobPostActivityNotFoundException::new);
        jobPostActivity.setJobDescription(jobPostActivityDto.getJobDescription());
        jobPostActivity.setJobTitle(jobPostActivityDto.getJobTitle());
        jobPostActivity.setJobType(jobPostActivityDto.getJobType());
        jobPostActivity.setRemote(jobPostActivityDto.getRemote());
        jobPostActivity.setSalary(jobPostActivityDto.getSalary());
        jobPostActivity.setJobCompany(jobCompany);
        jobPostActivity.setJobLocation(jobLocation);
        jobPostActivity.setAccount(account);
        return jobPostActivityRepository.save(jobPostActivity);
    }
}
