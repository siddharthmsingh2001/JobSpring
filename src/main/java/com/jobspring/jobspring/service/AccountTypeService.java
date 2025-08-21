package com.jobspring.jobspring.service;

import com.jobspring.jobspring.entity.AccountType;
import com.jobspring.jobspring.exception.AccountTypeNotFoundException;
import com.jobspring.jobspring.repo.AccountTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountTypeService {

    private final AccountTypeRepository accountTypeRepository;

    public List<AccountType> findAll(){
        return accountTypeRepository.findAll();
    }

    public AccountType findById(Long id) throws AccountTypeNotFoundException {
        return accountTypeRepository.findById(id).orElseThrow(AccountTypeNotFoundException::new);
    }

    public AccountType findByName(String name) throws AccountTypeNotFoundException{
        return accountTypeRepository.findByName(name).orElseThrow(AccountTypeNotFoundException::new);
    }

}
