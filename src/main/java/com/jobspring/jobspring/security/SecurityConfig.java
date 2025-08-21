package com.jobspring.jobspring.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityContextRepository securityContextRepository;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final AuthenticationProvider authenticationProvider;
    private final String[] PUBLIC_URL = {
            "/",
            "/register", "/verify-token",
            "/webjars/**", "/resources/**", "/assets/**", "/css/**", "/js/**", "/*.css", "/*.js", "/*.js.map", "/fonts/**", "/favicon.ico", "/error",
            "/login", "/logout"
    };

    @Bean
    public SecurityFilterChain homeFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(req->req
                        .requestMatchers(PUBLIC_URL).permitAll()
                        .requestMatchers("/recruiter-dashboard/**").hasAuthority("RECRUITER")
                        .requestMatchers("/seeker-dashboard/**").hasAuthority("SEEKER")
                        .anyRequest().authenticated()
                )
                .cors(Customizer.withDefaults())
                .csrf(csrf->csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .securityContext(context->context
                        .securityContextRepository(securityContextRepository)
                )
                .sessionManagement(session->session
                        .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::newSession)
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                )
                .authenticationProvider(authenticationProvider)
                .formLogin(form->form
                        .loginPage("/login").permitAll()
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .failureHandler(authenticationFailureHandler)
                        .successHandler(authenticationSuccessHandler)
                )
                .logout(logout->logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                );
        return http.build();
    }
}
