package com.jobspring.jobspring.service;

import com.jobspring.jobspring.dto.*;
import com.jobspring.jobspring.entity.*;
import com.jobspring.jobspring.exception.*;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrchestrationService {

    private final AccountService accountService;
    private final RecruiterProfileService recruiterProfileService;
    private final SeekerProfileService seekerProfileService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final TokenService tokenService;
    private final AccountTypeService accountTypeService;
    private final JobLocationService jobLocationService;
    private final JobCompanyService jobCompanyService;
    private final JobPostActivityService jobPostActivityService;
    private final SkillService skillService;
    private final SeekerApplyService seekerApplyService;
    private final SeekerSaveService seekerSaveService;
    private final ResumeService resumeService;

    public void registerUser(UserRegistrationDto userRegistrationDto) throws AccountTypeNotFoundException, MessagingException, AccountAlreadyExistsException, SeekerProfileAlreadyExistsException, RecruiterProfileAlreadyExistsException {
        var account = Account.builder()
                .email(userRegistrationDto.getEmail())
                .password(passwordEncoder.encode(userRegistrationDto.getPassword()))
                .accountType(accountTypeService.findById(userRegistrationDto.getAccountTypeDto().getId()))
                .locked(false)
                .enabled(false)
                .build();
        Account savedAccount = accountService.save(account);
        switch (account.getAccountType().getName()) {
            case AccountType.SEEKER:
                seekerProfileService.save(new SeekerProfile(), savedAccount);
                break;
            case AccountType.RECRUITER:
                recruiterProfileService.save(new RecruiterProfile(), savedAccount);
                break;
            default:
                throw new AccountTypeNotFoundException();
        }
        emailService.sendValidationMail(account);
    }

    public void activateAccount(VerificationTokenDto verificationTokenDto) throws AccountNotFoundException, InvalidTokenException {
        String token = verificationTokenDto.getToken();
        Account account = accountService.findByEmail(verificationTokenDto.getEmail());
        if (!account.getId().equals(tokenService.findIdByToken(token))) {
            throw new InvalidTokenException();
        }
        account.setEnabled(true);
        accountService.update(account);
        tokenService.deleteToken(token);
    }

    @SuppressWarnings("unchecked")
    public <Type extends Profile>Type getProfile(Class<Type> profileType) throws UnauthorizedUserException, AccountTypeNotFoundException, RecruiterProfileNotFoundException, SeekerProfileNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedUserException();
        }
        Account account = (Account) authentication.getPrincipal();
        return switch (account.getAccountType().getName()){
            case AccountType.RECRUITER -> {
                if (profileType.equals(RecruiterProfile.class)) {
                    yield (Type) recruiterProfileService.findByAccount(account);
                }
                throw new AccountTypeNotFoundException();
            }
            case AccountType.SEEKER -> {
                if (profileType.equals(SeekerProfile.class)) {
                    yield (Type) seekerProfileService.findByAccount(account);
                }
                throw new AccountTypeNotFoundException();
            }
            default -> throw new AccountTypeNotFoundException();
        };
    }

    public Account getAccount() throws UnauthorizedUserException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedUserException();
        }
        return (Account) authentication.getPrincipal();
    }

    public RecruiterProfile editRecruiterProfile(RecruiterProfileDto recruiterProfileDto, MultipartFile multipartFile) throws AccountNotFoundException, RecruiterProfileNotFoundException, IOException, UnauthorizedUserException {
        return recruiterProfileService.update(recruiterProfileDto, getAccount(), multipartFile);
    }

    public List<JobPostActivity> fetchJobPostsByRecruiter(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();
        return jobPostActivityService.findByAccount(account);
    }

    public List<JobPostActivity> fetchAllJobPosts(){
        return jobPostActivityService.findAll();
    }

    public JobPostActivity saveJobPostActivity(JobPostActivityDto jobPostActivityDto) throws UnauthorizedUserException {
        JobLocation jobLocation = jobLocationService.create(jobPostActivityDto.getJobLocationDto());
        JobCompany jobCompany = jobCompanyService.create(jobPostActivityDto.getJobCompanyDto());
        Account account = getAccount();
        return jobPostActivityService.create(jobPostActivityDto, account, jobLocation, jobCompany);
    }

    public JobPostActivity editJobPostActivity(JobPostActivityDto jobPostActivityDto) throws JobLocationNotFoundException, UnauthorizedUserException, JobPostActivityNotFoundException {
        JobCompany jobCompany = jobCompanyService.update(jobPostActivityDto.getJobCompanyDto());
        JobLocation jobLocation = jobLocationService.update(jobPostActivityDto.getJobLocationDto());
        Account account = getAccount();
        return jobPostActivityService.update(jobPostActivityDto, account, jobLocation, jobCompany);
    }

    public JobPostActivity getJobPostActivity(Long id) throws JobPostActivityNotFoundException {
        return jobPostActivityService.findById(id);
    }

    public void deleteJobPostActivity(Long id) throws JobPostActivityNotFoundException{
        jobPostActivityService.deleteById(id);
    }

    public SeekerProfile editSeekerProfile(SeekerProfileDto seekerProfileDto, MultipartFile multipartImage, MultipartFile multipartPdf) throws UnauthorizedUserException, SeekerProfileNotFoundException, IOException, SkillNotFoundException {
        SeekerProfile seekerProfile = seekerProfileService.findByAccount(getAccount());
        List<Skill> skills = skillService.update(seekerProfileDto.getSkills(), seekerProfile);
        seekerProfile = seekerProfileService.update(seekerProfileDto, getAccount(), multipartImage, multipartPdf);
        return seekerProfile;
    }

    public List<SeekerProfileDto> getSeekersByJobPost(Long jobPostActivityId) throws JobPostActivityNotFoundException, RecruiterProfileNotFoundException, AccountTypeNotFoundException, UnauthorizedUserException, SeekerProfileNotFoundException {
        JobPostActivity jobPostActivity = jobPostActivityService.findById(jobPostActivityId);
        List<SeekerApply> seekerApplyList = seekerApplyService.readByJobPostActivity(jobPostActivity);
        List<SeekerProfileDto> seekerProfileDtos = new ArrayList<>();
        for(SeekerApply seekerApply: seekerApplyList){
            seekerProfileDtos.add(
                    new SeekerProfileDto(seekerApply.getSeekerProfile())
            );
        }
        return seekerProfileDtos;
    }

    public SeekerApplyDto getCurrentSeekerApply(Long jobPostActivityId) throws JobPostActivityNotFoundException, UnauthorizedUserException, SeekerProfileNotFoundException {
        JobPostActivity jobPostActivity = jobPostActivityService.findById(jobPostActivityId);
        SeekerProfile seekerProfile = seekerProfileService.findByAccount(getAccount());
        Optional<SeekerApply> opt = seekerApplyService.readByAccountAndPost(seekerProfile, jobPostActivity);
        return opt.map(SeekerApplyDto::new).orElseGet(SeekerApplyDto::new);
    }

    // The SeekerApplyDto is not really used ever but keep it still.
    public SeekerApplyDto saveCurrentSeekerApply(Long jobPostActivityId, SeekerApplyDto seekerApplyDto) throws RecruiterProfileNotFoundException, AccountTypeNotFoundException, UnauthorizedUserException, SeekerProfileNotFoundException, JobPostActivityNotFoundException, SeekerSaveAlreadyExistsException {
        SeekerProfile seekerProfile = getProfile(SeekerProfile.class);
        JobPostActivity jobPostActivity = jobPostActivityService.findById(jobPostActivityId);
        SeekerApply seekerApply = seekerApplyService.create(seekerProfile, jobPostActivity);
        return new SeekerApplyDto(seekerApply);
    }

    // The SeekerSaveDto is not really used ever but keep it still.
    public SeekerSaveDto saveCurrentSeekerSave(Long jobPostActivityId, SeekerSaveDto seekerSaveDto) throws RecruiterProfileNotFoundException, AccountTypeNotFoundException, UnauthorizedUserException, SeekerProfileNotFoundException, JobPostActivityNotFoundException, SeekerSaveAlreadyExistsException {
        SeekerProfile seekerProfile = getProfile(SeekerProfile.class);
        JobPostActivity jobPostActivity = jobPostActivityService.findById(jobPostActivityId);
        SeekerSave seekerSave = seekerSaveService.create(seekerProfile, jobPostActivity);
        return new SeekerSaveDto(seekerSave);
    }

    public SeekerSaveDto getCurrentSeekerSave(Long jobPostActivityId) throws JobPostActivityNotFoundException, UnauthorizedUserException, SeekerProfileNotFoundException{
        JobPostActivity jobPostActivity = jobPostActivityService.findById(jobPostActivityId);
        SeekerProfile seekerProfile = seekerProfileService.findByAccount(getAccount());
        Optional<SeekerSave> opt = seekerSaveService.readByAccountAndPost(seekerProfile,jobPostActivity);
        return opt.map(SeekerSaveDto::new).orElseGet(SeekerSaveDto::new);
    }

    public List<JobPostActivityDto> getJobPostActivityBySeekerSave() throws RecruiterProfileNotFoundException, AccountTypeNotFoundException, UnauthorizedUserException, SeekerProfileNotFoundException {
        SeekerProfile seekerProfile = getProfile(SeekerProfile.class);
        List<SeekerSave> seekerSaves = seekerSaveService.readBySeekerProfile(seekerProfile);
        List<JobPostActivityDto> jobPostActivityDtos = new ArrayList<>();
        for(SeekerSave seekerSave: seekerSaves){
            jobPostActivityDtos.add(
                    new JobPostActivityDto(seekerSave.getJobPostActivity())
            );
        }
        return jobPostActivityDtos;
    }

    public Resource getSeekerResume(String fileName){
        Resource resource = null;
        try{
            return resumeService.getFileAsResource(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}