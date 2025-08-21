package com.jobspring.jobspring.service;

import com.jobspring.jobspring.dto.JobLocationDto;
import com.jobspring.jobspring.entity.JobLocation;
import com.jobspring.jobspring.exception.JobLocationNotFoundException;
import com.jobspring.jobspring.repo.JobLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobLocationService {

    private final JobLocationRepository jobLocationRepository;

    public JobLocation create(JobLocationDto jobLocationDto){
        JobLocation jobLocation = JobLocation.builder()
                .city(jobLocationDto.getCity())
                .state(jobLocationDto.getState())
                .country(jobLocationDto.getCountry())
                .build();
        return jobLocationRepository.save(jobLocation);
    }

    public JobLocation update(JobLocationDto jobLocationDto) throws JobLocationNotFoundException {
        JobLocation jobLocation = jobLocationRepository.findById(jobLocationDto.getId()).orElseThrow(JobLocationNotFoundException::new);
        jobLocation.setCity(jobLocationDto.getCity());
        jobLocation.setState(jobLocationDto.getState());
        jobLocation.setCountry(jobLocationDto.getCountry());
        return jobLocationRepository.save(jobLocation);
    }
}
