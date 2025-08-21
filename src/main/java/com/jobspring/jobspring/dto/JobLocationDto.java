package com.jobspring.jobspring.dto;

import com.jobspring.jobspring.entity.JobLocation;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobLocationDto {

    private Long id;
    private String city;
    private String state;
    private String country;

    public JobLocationDto(JobLocation jobLocation){
        this.id = jobLocation.getId();
        this.city = jobLocation.getCity();
        this.state = jobLocation.getState();
        this.country = jobLocation.getCountry();
    }
}
