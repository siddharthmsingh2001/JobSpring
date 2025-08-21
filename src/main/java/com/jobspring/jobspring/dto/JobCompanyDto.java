package com.jobspring.jobspring.dto;

import com.jobspring.jobspring.entity.JobCompany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobCompanyDto {

    private Long id;
    private String logo;
    private String name;

    public JobCompanyDto(JobCompany jobCompany){
        this.id = jobCompany.getId();
        this.logo = jobCompany.getLogo();
        this.name = jobCompany.getName();
    }

}
