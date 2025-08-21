package com.jobspring.jobspring.dto;

import com.jobspring.jobspring.entity.AccountType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto implements Serializable {

    @Email(message = "Email is not valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @Size(min = 3, max = 10, message = "Password should be between 1-10 characters")
    @NotBlank(message = "Password cannot be blank")
    private String password;

    private AccountTypeDto accountTypeDto;

}
