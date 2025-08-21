package com.jobspring.jobspring.controller;

import com.jobspring.jobspring.dto.*;
import com.jobspring.jobspring.entity.JobPostActivity;
import com.jobspring.jobspring.entity.SeekerApply;
import com.jobspring.jobspring.entity.SeekerProfile;
import com.jobspring.jobspring.entity.Skill;
import com.jobspring.jobspring.exception.*;
import com.jobspring.jobspring.service.OrchestrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class SeekerDashboardController {

    private final OrchestrationService orchestrationService;

    @GetMapping("/seeker-dashboard")
    public String viewSeekerDashboard(
            Model model
    ) throws RecruiterProfileNotFoundException, AccountTypeNotFoundException, UnauthorizedUserException, SeekerProfileNotFoundException {
        SeekerProfileDto seekerProfile = new SeekerProfileDto(
                orchestrationService.getProfile(SeekerProfile.class)
        );
        List<JobPostActivity> posts = orchestrationService.fetchAllJobPosts();
        List<JobPostActivityDto> jobPosts = posts.stream().map(JobPostActivityDto::new).toList();
        model.addAttribute("username", seekerProfile.getAccountDto().getEmail());
        model.addAttribute("seekerProfileDto", seekerProfile);
        model.addAttribute("jobPosts", jobPosts);
        return "seeker/seeker-dashboard";
    }

    @GetMapping("/seeker-dashboard/profile")
    public String viewSeekerProfilePage(
            Model model
    ) throws RecruiterProfileNotFoundException, AccountTypeNotFoundException, UnauthorizedUserException, SeekerProfileNotFoundException {
        SeekerProfileDto seekerProfileDto = new SeekerProfileDto(
              orchestrationService.getProfile(SeekerProfile.class)
        );
        if (seekerProfileDto.getSkills() == null || seekerProfileDto.getSkills().isEmpty()) {
            seekerProfileDto.setSkills(new ArrayList<>());
            seekerProfileDto.getSkills().add(new SkillDto());
        }
        model.addAttribute("seekerProfileDto", seekerProfileDto);
        return "seeker/seeker-profile";
    }

    @PostMapping("/seeker-dashboard/profile")
    public String editSeekerProfilePage(
            @ModelAttribute("recruiterProfileDto") SeekerProfileDto seekerProfileDto,
            @RequestParam("image") MultipartFile multipartImage,
            @RequestParam("pdf") MultipartFile multipartPdf,
            Model model
    ) throws UnauthorizedUserException, SeekerProfileNotFoundException, IOException, SkillNotFoundException {
        orchestrationService.editSeekerProfile(seekerProfileDto, multipartImage, multipartPdf);
        return "redirect:/seeker-dashboard";
    }

    @GetMapping("/seeker-dashboard/view-job-post/{id}")
    public String viewJobPost(
            @PathVariable Long id,
            Model model
    ) throws RecruiterProfileNotFoundException, AccountTypeNotFoundException, UnauthorizedUserException, SeekerProfileNotFoundException, JobPostActivityNotFoundException {
        SeekerProfileDto seekerProfileDto = new SeekerProfileDto(
                orchestrationService.getProfile(SeekerProfile.class)
        );
        model.addAttribute("username", seekerProfileDto.getAccountDto().getEmail());
        model.addAttribute("seekerProfileDto", seekerProfileDto);
        model.addAttribute("jobPostActivityDto", new JobPostActivityDto(orchestrationService.getJobPostActivity(id)));
        model.addAttribute("seekerApplyDto", orchestrationService.getCurrentSeekerApply(id));
        model.addAttribute("seekerSaveDto", orchestrationService.getCurrentSeekerSave(id));
        return "seeker/seeker-job-view";
    }

    @PostMapping("/seeker-dashboard/apply-job/{id}")
    public String applyJobPost(
            @PathVariable Long id,
            @ModelAttribute("seekerApplyDto") SeekerApplyDto seekerApplyDto,
            Model model
    ) throws RecruiterProfileNotFoundException, AccountTypeNotFoundException, SeekerSaveAlreadyExistsException, UnauthorizedUserException, SeekerProfileNotFoundException, JobPostActivityNotFoundException {
        orchestrationService.saveCurrentSeekerApply(id, seekerApplyDto);
        return "redirect:/seeker-dashboard/view-job-post/"+id;
    }

    @PostMapping("/seeker-dashboard/save-job/{id}")
    public String saveJobPost(
            @PathVariable Long id,
            @ModelAttribute("seekerApplyDto") SeekerSaveDto seekerSaveDto,
            Model model
    ) throws RecruiterProfileNotFoundException, AccountTypeNotFoundException, SeekerSaveAlreadyExistsException, UnauthorizedUserException, SeekerProfileNotFoundException, JobPostActivityNotFoundException {
        orchestrationService.saveCurrentSeekerSave(id, seekerSaveDto);
        return "redirect:/seeker-dashboard/view-job-post/"+id;
    }

    @GetMapping("/seeker-dashboard/saved-jobs")
    public String savedJobPosts(
        Model model
    ) throws RecruiterProfileNotFoundException, AccountTypeNotFoundException, UnauthorizedUserException, SeekerProfileNotFoundException {
        SeekerProfileDto seekerProfile = new SeekerProfileDto(
                orchestrationService.getProfile(SeekerProfile.class)
        );
        List<JobPostActivityDto> seekerSaveDtos = orchestrationService.getJobPostActivityBySeekerSave();
        System.out.println("Print "+seekerSaveDtos.get(0).getJobTitle());
        model.addAttribute("username", seekerProfile.getAccountDto().getEmail());
        model.addAttribute("seekerProfileDto", seekerProfile);
        model.addAttribute("jobPosts", seekerSaveDtos);
        return "seeker/seeker-save";
    }
}