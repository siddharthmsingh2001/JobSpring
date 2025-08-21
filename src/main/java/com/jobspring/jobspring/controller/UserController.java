package com.jobspring.jobspring.controller;

import com.jobspring.jobspring.dto.AccountTypeDto;
import com.jobspring.jobspring.dto.AuthenticationRequest;
import com.jobspring.jobspring.dto.UserRegistrationDto;
import com.jobspring.jobspring.dto.VerificationTokenDto;
import com.jobspring.jobspring.exception.*;
import com.jobspring.jobspring.service.AccountTypeService;
import com.jobspring.jobspring.service.OrchestrationService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final AccountTypeService accountTypeService;
    private final OrchestrationService orchestrationService;

    @GetMapping("/register")
    public String showRegistrationPage(Model model){
        List<AccountTypeDto> accountTypeList = accountTypeService.findAll().stream()
                        .map(AccountTypeDto::new).toList();
        model.addAttribute("getAllTypes", accountTypeList);
        if(!model.containsAttribute("userRegistrationDto")){
            model.addAttribute("userRegistrationDto", new UserRegistrationDto());
        }
        return "common/register";
    }

    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute("userRegistrationDto") @Valid UserRegistrationDto userRegistrationDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) throws MessagingException, AccountTypeNotFoundException, AccountAlreadyExistsException, SeekerProfileAlreadyExistsException, RecruiterProfileAlreadyExistsException {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDto", bindingResult);
            redirectAttributes.addFlashAttribute("userRegistrationDto", userRegistrationDto);
            return "redirect:/register";
        }
        redirectAttributes.addFlashAttribute("verificationTokenDto", new VerificationTokenDto(userRegistrationDto.getEmail()));
        orchestrationService.registerUser(userRegistrationDto);
        return "redirect:/verify-token";
    }

    @GetMapping("/verify-token")
    public String showTokenVerificationPage(
            Model model
    ){
        if(!model.containsAttribute("verificationTokenDto")){
            model.addAttribute("verificationTokenDto", new VerificationTokenDto());
        }
        return "common/token-verification";
    }

    @PostMapping("/verify-token")
    public String verifyToken(
            @ModelAttribute("verificationTokenDto") VerificationTokenDto verificationTokenDto,
            RedirectAttributes redirectAttributes
    ) throws AccountNotFoundException, InvalidTokenException, AccountAlreadyExistsException {
        orchestrationService.activateAccount(verificationTokenDto);
        redirectAttributes.addFlashAttribute("email", verificationTokenDto.getEmail());
        return "common/registration-success";
    }

    @GetMapping("/login")
    public String showLoginPage(
            Model model
    ){
        if(!model.containsAttribute("authenticationRequest")){
            model.addAttribute("authenticationRequest", new AuthenticationRequest());
        }
        return "common/login";
    }
}
