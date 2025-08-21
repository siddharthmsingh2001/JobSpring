package com.jobspring.jobspring.service;

import com.jobspring.jobspring.entity.Account;
import com.jobspring.jobspring.exception.AccountAlreadyExistsException;
import com.jobspring.jobspring.exception.AccountNotFoundException;
import com.jobspring.jobspring.repo.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return accountRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("Account with given Email does not exist"));
    }

    public Account findById(Long id) throws AccountNotFoundException {
        return accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
    }

    @Transactional
    public Account save(Account account) throws AccountAlreadyExistsException {
        Optional<Account> optional = accountRepository.findByEmail(account.getEmail());
        if(optional.isPresent()){
            throw new AccountAlreadyExistsException();
        }
        return accountRepository.save(account);
    }

    @Transactional
    public Account update(Account account) throws AccountNotFoundException {
        return accountRepository.save(account);
    }

    public Account findByEmail(String email) throws AccountNotFoundException {
        return accountRepository.findByEmail(email).orElseThrow(AccountNotFoundException::new);
    }
}
