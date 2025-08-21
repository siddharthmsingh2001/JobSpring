package com.jobspring.jobspring.service;

import com.jobspring.jobspring.dto.JobCompanyDto;
import com.jobspring.jobspring.entity.JobCompany;
import com.jobspring.jobspring.exception.JobLocationNotFoundException;
import com.jobspring.jobspring.repo.JobCompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobCompanyService {

    private final JobCompanyRepository jobCompanyRepository;

    public JobCompany create(JobCompanyDto jobCompanyDto){
        jobCompanyDto.setId(null);
        Optional<JobCompany> optional = jobCompanyRepository.findByName(jobCompanyDto.getName());
        if(optional.isPresent()){
            return optional.get();
        }
        JobCompany jobCompany = JobCompany.builder()
                .name(jobCompanyDto.getName())
                .logo(jobCompanyDto.getLogo())
                .build();
        return jobCompanyRepository.save(jobCompany);
    }

    public JobCompany update(JobCompanyDto jobCompanyDto){
        return jobCompanyRepository.findByName(jobCompanyDto.getName()).orElse(create(jobCompanyDto));
    }
}
