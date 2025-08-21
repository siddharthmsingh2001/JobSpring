package com.jobspring.jobspring.exception;

import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountAlreadyExistsException.class)
    public String handleDuplicateAccount(
            AccountAlreadyExistsException cause,
            RedirectAttributes redirectAttributes
    ){
        redirectAttributes.addFlashAttribute("errorMessage", cause.getMessage());
        return "redirect:/login";
    }

    @ExceptionHandler(MessagingException.class)
    public String handleMessaging(
            MessagingException cause,
            RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("errorMessage",cause.getMessage());
        return "redirect:/login";
    }

    @ExceptionHandler(InvalidTokenException.class)
    public String handleInvalidToken(
            InvalidTokenException cause,
            RedirectAttributes redirectAttributes
    ){
        redirectAttributes.addFlashAttribute("errorMessage", cause.getMessage());
        return "redirect:/verify-token";
    }
}
