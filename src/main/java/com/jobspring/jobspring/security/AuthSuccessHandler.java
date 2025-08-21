package com.jobspring.jobspring.security;

import com.jobspring.jobspring.entity.AccountType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("SEEKER");
        switch (role) {
            case AccountType.RECRUITER:
                response.sendRedirect("/recruiter-dashboard");
                break;
            case AccountType.SEEKER:
                response.sendRedirect("/seeker-dashboard");
                break;
            default:
                response.sendRedirect("/");
                break;
        }
    }
}
