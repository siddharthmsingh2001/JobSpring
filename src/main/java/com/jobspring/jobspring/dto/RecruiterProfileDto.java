package com.jobspring.jobspring.dto;

import com.jobspring.jobspring.entity.RecruiterProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecruiterProfileDto {

    private AccountDto accountDto;
    private Long accountId;
    private String company;
    private String firstName;
    private String lastName;
    private String profilePhoto;
    private String city;
    private String state;
    private String country;
    private String profilePhotoUrl;

    public RecruiterProfileDto(RecruiterProfile recruiterProfile){
        this.accountDto = new AccountDto(recruiterProfile.getAccount());
        this.accountId = accountDto.getId();
        this.company = recruiterProfile.getCompany();
        this.firstName = recruiterProfile.getFirstName();
        this.lastName = recruiterProfile.getLastName();
        this.profilePhoto = recruiterProfile.getProfilePhoto();
        this.city = recruiterProfile.getCity();
        this.state = recruiterProfile.getState();
        this.country = recruiterProfile.getCountry();
        this.profilePhotoUrl = getPhotoUrl();
    }

    private String getPhotoUrl(){
        return "/profile-photos/recruiter/" + profilePhoto;
    }
}