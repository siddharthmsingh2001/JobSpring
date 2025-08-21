package com.jobspring.jobspring.service;

import com.jobspring.jobspring.dto.SeekerProfileDto;
import com.jobspring.jobspring.entity.Account;
import com.jobspring.jobspring.entity.SeekerProfile;
import com.jobspring.jobspring.exception.AccountNotFoundException;
import com.jobspring.jobspring.exception.SeekerProfileAlreadyExistsException;
import com.jobspring.jobspring.exception.SeekerProfileNotFoundException;
import com.jobspring.jobspring.repo.SeekerProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SeekerProfileService {

    private final SeekerProfileRepository seekerProfileRepository;
    private final ProfilePhotoService profilePhotoService;
    private final ResumeService resumeService;

    @Transactional
    public SeekerProfile save(SeekerProfile seekerProfile, Account account) throws SeekerProfileAlreadyExistsException {
        if(seekerProfileRepository.existsByAccount(account)){
            throw new SeekerProfileAlreadyExistsException();
        }
        seekerProfile.setAccount(account);
        return seekerProfileRepository.save(seekerProfile);
    }

    public SeekerProfile findByAccount(Account account) throws  SeekerProfileNotFoundException {
        return seekerProfileRepository.findByAccount(account).orElseThrow(SeekerProfileNotFoundException::new);
    }

    @Transactional
    public SeekerProfile update(SeekerProfileDto seekerProfileDto, Account account, MultipartFile multipartImage, MultipartFile multipartPdf) throws SeekerProfileNotFoundException, IOException {
        SeekerProfile seekerProfile = seekerProfileRepository.findByAccount(account).orElseThrow(SeekerProfileNotFoundException::new);
        seekerProfile.setFirstName(seekerProfileDto.getFirstName());
        seekerProfile.setLastName(seekerProfileDto.getLastName());
        seekerProfile.setCity(seekerProfileDto.getCity());
        seekerProfile.setState(seekerProfileDto.getState());
        seekerProfile.setCountry(seekerProfileDto.getCountry());
        seekerProfile.setEmploymentType(seekerProfileDto.getEmploymentType());
        seekerProfile.setWorkAuthorization(seekerProfileDto.getWorkAuthorization());
        if (!multipartImage.isEmpty()){
            seekerProfile.setProfilePhoto(
                    profilePhotoService.storeProfilePhoto(account.getId(), multipartImage, account.getAccountType())
            );
        }
        if (multipartPdf != null && !multipartPdf.isEmpty()) {
            seekerProfile.setResume(
                    resumeService.storeResume(account.getId(), multipartPdf)
            );
        }
        return seekerProfileRepository.save(seekerProfile);
    }

}
