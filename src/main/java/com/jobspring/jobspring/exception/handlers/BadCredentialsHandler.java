package com.jobspring.jobspring.exception.handlers;

import com.jobspring.jobspring.service.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class BadCredentialsHandler implements AuthExceptionHandler {

    private final AccountService accountService;
    private final RedirectStrategy redirectStrategy;

    @Override
    public Class<? extends AuthenticationException> getExceptionType() {
        return BadCredentialsException.class;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        FlashMap outputFlashMap = new FlashMap();
        try{
            accountService.loadUserByUsername(request.getParameter("email"));
            outputFlashMap.put("errorMessage", "Password is Invalid");
        } catch (UsernameNotFoundException e) {
            outputFlashMap.put("errorMessage", "Email is Invalid");
        }finally {
            new SessionFlashMapManager().saveOutputFlashMap(outputFlashMap, request, response);
            redirectStrategy.sendRedirect(request,response,"/login");
        }
    }
}
