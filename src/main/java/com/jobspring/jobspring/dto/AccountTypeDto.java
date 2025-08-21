package com.jobspring.jobspring.dto;

import com.jobspring.jobspring.entity.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountTypeDto implements Serializable {

    public AccountTypeDto(AccountType accountType){
        this.id = accountType.getId();
        this.name = accountType.getName();
    }

    private Long id;
    private String name;

}
