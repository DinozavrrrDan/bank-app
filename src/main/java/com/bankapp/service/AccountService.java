package com.bankapp.service;

import com.bankapp.enteties.Account;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface AccountService {
    Optional<Account> getByLogin(@NonNull String login);

    Account createAccount(Account account);
}
