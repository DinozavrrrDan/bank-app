package com.bankapp.service.impl;

import com.bankapp.enteties.Account;
import com.bankapp.repository.AccountRepository;
import com.bankapp.service.AccountService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    public AccountServiceImpl(List<Account> accounts, PasswordEncoder passwordEncoder, AccountRepository accountRepository) {
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> getByLogin(String login) {
        return accountRepository.findAll().stream()
                .filter(user -> login.equals(user.getLogin()))
                .findFirst();
    }

    @Override
    public Account createAccount(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }
}
