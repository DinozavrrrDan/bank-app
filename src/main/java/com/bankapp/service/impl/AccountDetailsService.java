package com.bankapp.service.impl;

import com.bankapp.enteties.Account;
import com.bankapp.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountDetailsService implements UserDetailsService {

    private AccountRepository accountRepository;

    @Override
    public AccountDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> user = accountRepository.findByLogin(username);
        return user.map(AccountDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + "NOT_FOUND"));
    }
}
