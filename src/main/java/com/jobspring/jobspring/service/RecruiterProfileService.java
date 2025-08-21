package com.jobspring.jobspring.service;

import com.jobspring.jobspring.dto.RecruiterProfileDto;
import com.jobspring.jobspring.entity.Account;
import com.jobspring.jobspring.entity.RecruiterProfile;
import com.jobspring.jobspring.exception.RecruiterProfileAlreadyExistsException;
import com.jobspring.jobspring.exception.RecruiterProfileNotFoundException;
import com.jobspring.jobspring.repo.RecruiterProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class RecruiterProfileService {

    private final RecruiterProfileRepository recruiterProfileRepository;
    private final ProfilePhotoService profilePhotoService;

    @Transactional
    public RecruiterProfile save(RecruiterProfile recruiterProfile, Account account) throws RecruiterProfileAlreadyExistsException {
        if(recruiterProfileRepository.existsByAccount(account)){
            throw new RecruiterProfileAlreadyExistsException();
        }
        recruiterProfile.setAccount(account);
        return recruiterProfileRepository.save(recruiterProfile);
    }

    public RecruiterProfile findByAccount(Account account) throws RecruiterProfileNotFoundException{
        return recruiterProfileRepository.findByAccount(account).orElseThrow(RecruiterProfileNotFoundException::new);
    }

    @Transactional
    public RecruiterProfile update(RecruiterProfileDto recruiterProfileDto, Account account, MultipartFile multipartFile) throws RecruiterProfileNotFoundException, IOException {
        RecruiterProfile recruiterProfile = recruiterProfileRepository.findByAccount(account).orElseThrow(RecruiterProfileNotFoundException::new);
        recruiterProfile.setFirstName(recruiterProfileDto.getFirstName());
        recruiterProfile.setLastName(recruiterProfileDto.getLastName());
        recruiterProfile.setCompany(recruiterProfileDto.getCompany());
        recruiterProfile.setCity(recruiterProfileDto.getCity());
        recruiterProfile.setState(recruiterProfileDto.getState());
        recruiterProfile.setCountry(recruiterProfileDto.getCountry());

        if (!multipartFile.isEmpty()) {
            recruiterProfile.setProfilePhoto(
                    profilePhotoService.storeProfilePhoto(account.getId(), multipartFile, account.getAccountType())
            );
        }
        return recruiterProfileRepository.save(recruiterProfile);
    }
}
