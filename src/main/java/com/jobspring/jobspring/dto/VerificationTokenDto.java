package com.jobspring.jobspring.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VerificationTokenDto implements Serializable {
    private String email;
    private String token;

    public VerificationTokenDto(String email){
        this.email = email;
    }
}
