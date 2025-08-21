package com.jobspring.jobspring.dto;

import com.jobspring.jobspring.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private Long id;
    private String email;
    private AccountTypeDto accountTypeDto;

    public AccountDto(Account account){
        this.id = account.getId();
        this.email = account.getEmail();
        this.accountTypeDto = new AccountTypeDto(account.getAccountType());
    }
}