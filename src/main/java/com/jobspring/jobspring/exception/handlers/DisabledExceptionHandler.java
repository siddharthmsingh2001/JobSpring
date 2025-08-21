package com.jobspring.jobspring.exception.handlers;

import com.jobspring.jobspring.dto.VerificationTokenDto;
import com.jobspring.jobspring.entity.Account;
import com.jobspring.jobspring.service.AccountService;
import com.jobspring.jobspring.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class DisabledExceptionHandler implements AuthExceptionHandler {

    private final AccountService accountService;
    private final EmailService emailService;
    private final RedirectStrategy redirectStrategy;

    @Override
    public Class<? extends AuthenticationException> getExceptionType() {
        return DisabledException.class;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException{
        String username = request.getParameter("email");
        Account account = (Account) accountService.loadUserByUsername(username);
        try {
            emailService.sendValidationMail(account);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send verification email", e);
        }
        FlashMap outputFlashMap = new FlashMap();
        outputFlashMap.put("errorMessage", "Your account is disabled, Please enable it");
        outputFlashMap.put("verificationTokenDto", new VerificationTokenDto(username));
        new SessionFlashMapManager().saveOutputFlashMap(outputFlashMap, request, response);
        redirectStrategy.sendRedirect(request,response,"/verify-token");
    }
}
