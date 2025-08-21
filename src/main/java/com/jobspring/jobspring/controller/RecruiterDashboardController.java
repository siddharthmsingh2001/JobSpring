package com.jobspring.jobspring.controller;

import com.jobspring.jobspring.dto.JobPostActivityDto;
import com.jobspring.jobspring.dto.RecruiterProfileDto;
import com.jobspring.jobspring.entity.JobPostActivity;
import com.jobspring.jobspring.entity.RecruiterProfile;
import com.jobspring.jobspring.exception.*;
import com.jobspring.jobspring.service.OrchestrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Controller
public class RecruiterDashboardController {

    private final OrchestrationService orchestrationService;

    @GetMapping("/recruiter-dashboard")
    public String viewRecruiterDashboard(
            Model model
    ) throws RecruiterProfileNotFoundException, AccountTypeNotFoundException, UnauthorizedUserException, SeekerProfileNotFoundException {
        RecruiterProfileDto recruiterProfile = new RecruiterProfileDto(
                orchestrationService.getProfile(RecruiterProfile.class)
        );
        model.addAttribute("username", recruiterProfile.getAccountDto().getEmail());
        model.addAttribute("recruiterProfileDto", recruiterProfile);
        List<JobPostActivity> posts = orchestrationService.fetchJobPostsByRecruiter();
        List<JobPostActivityDto> jobPosts = posts.stream().map(JobPostActivityDto::new).toList();
        model.addAttribute("jobPosts", jobPosts);
        return "recruiter/recruiter-dashboard";
    }

    @GetMapping("/recruiter-dashboard/profile")
    public String viewRecruiterProfilePage(
            Model model
    ) throws RecruiterProfileNotFoundException, AccountTypeNotFoundException, UnauthorizedUserException, SeekerProfileNotFoundException {
        RecruiterProfileDto recruiterProfile = new RecruiterProfileDto(
                orchestrationService.getProfile(RecruiterProfile.class)
        );
        model.addAttribute("recruiterProfileDto", recruiterProfile);
        return "recruiter/recruiter-profile";
    }

    @PostMapping("/recruiter-dashboard/profile")
    public String editRecruiterProfilePage(
            Model model,
            @ModelAttribute("recruiterProfileDto") RecruiterProfileDto recruiterProfile,
            @RequestParam("image") MultipartFile multipartFile
    ) throws RecruiterProfileNotFoundException, AccountNotFoundException, IOException, UnauthorizedUserException {
        orchestrationService.editRecruiterProfile(recruiterProfile, multipartFile);
        return "redirect:/recruiter-dashboard";
    }

    @GetMapping("/recruiter-dashboard/add-job-post")
    public String viewJobPostPage(
            Model model
    ){
        model.addAttribute("jobPostActivityDto", new JobPostActivityDto());
        return "recruiter/recruiter-job-post";
    }

    @PostMapping("/recruiter-dashboard/add-job-post")
    public String addJobPost(
            JobPostActivityDto jobPostActivityDto
    ) throws UnauthorizedUserException, JobLocationNotFoundException, JobPostActivityNotFoundException {
        if(jobPostActivityDto.getId() == null){
            orchestrationService.saveJobPostActivity(jobPostActivityDto);
            return "redirect:/recruiter-dashboard";
        } else{
            orchestrationService.editJobPostActivity(jobPostActivityDto);
            return "redirect:/recruiter-dashboard/view-job-post/" + jobPostActivityDto.getId();
        }
    }

    @GetMapping("/recruiter-dashboard/view-job-post/{id}")
    public String viewJobPost(
            @PathVariable Long id,
            Model model
    ) throws JobPostActivityNotFoundException, RecruiterProfileNotFoundException, AccountTypeNotFoundException, UnauthorizedUserException, SeekerProfileNotFoundException {
        RecruiterProfileDto recruiterProfile = new RecruiterProfileDto(
                orchestrationService.getProfile(RecruiterProfile.class)
        );
        model.addAttribute("username", recruiterProfile.getAccountDto().getEmail());
        model.addAttribute("recruiterProfileDto", recruiterProfile);
        model.addAttribute("jobPostActivityDto", new JobPostActivityDto(orchestrationService.getJobPostActivity(id)));
        model.addAttribute("appliedSeekers", orchestrationService.getSeekersByJobPost(id));
        return "recruiter/recruiter-job-view";
    }

    @PostMapping("/recruiter-dashboard/delete-job-post/{id}")
    public String deleteJobPost(
            @PathVariable Long id
    ) throws JobPostActivityNotFoundException{
        orchestrationService.deleteJobPostActivity(id);
        return "redirect:/recruiter-dashboard";
    }

    @GetMapping("/recruiter-dashboard/edit-job-post/{id}")
    public String editJobPost(
            @PathVariable Long id,
            Model model
    ) throws JobPostActivityNotFoundException{
        JobPostActivityDto jobPostActivityDto = new JobPostActivityDto(orchestrationService.getJobPostActivity(id));
        model.addAttribute("jobPostActivityDto", jobPostActivityDto);
        return "recruiter/recruiter-job-post";
    }

    @GetMapping("/recruiter-dashboard/view-job-post/seeker-resume/{file}")
    public ResponseEntity<?> downloadSeekerResume(
            @PathVariable String file
    ){
        Resource resource = orchestrationService.getSeekerResume(file);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"")
                .body(resource);
    }
}
