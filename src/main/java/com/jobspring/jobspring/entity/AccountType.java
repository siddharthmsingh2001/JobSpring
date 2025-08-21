package com.jobspring.jobspring.entity;

import com.jobspring.jobspring.dto.AccountTypeDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "account_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class AccountType implements Serializable {

    public static final String RECRUITER = "RECRUITER";
    public static final String SEEKER = "SEEKER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 15)
    private String name;

    @OneToMany(targetEntity = Account.class, mappedBy = "accountType", cascade = CascadeType.ALL)
    private List<Account> accountList;
}