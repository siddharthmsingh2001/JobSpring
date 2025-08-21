package com.jobspring.jobspring.dto;

import com.jobspring.jobspring.entity.SeekerProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeekerProfileDto {

    private AccountDto accountDto;
    private Long accountId;
    private String firstName;
    private String lastName;
    private String profilePhoto;
    private String city;
    private String state;
    private String country;
    private String employmentType;
    private String workAuthorization;
    private String resume;
    private List<SkillDto> skills;
    private String profilePhotoUrl;

    public SeekerProfileDto (SeekerProfile seekerProfile){
        this.accountDto = new AccountDto(seekerProfile.getAccount());
        this.accountId = accountDto.getId();
        this.firstName = seekerProfile.getFirstName();
        this.lastName = seekerProfile.getLastName();
        this.profilePhoto = seekerProfile.getProfilePhoto();
        this.city = seekerProfile.getCity();
        this.state = seekerProfile.getCity();
        this.country = seekerProfile.getCountry();
        this.employmentType = seekerProfile.getEmploymentType();
        this.workAuthorization = seekerProfile.getWorkAuthorization();
        this.resume = seekerProfile.getResume();
        this.skills = seekerProfile.getSkills().stream().map(SkillDto::new).toList();
        this.profilePhotoUrl = getPhotoUrl();
    }

    private String getPhotoUrl(){
        return "/profile-photos/seeker/" + profilePhoto;
    }
}
