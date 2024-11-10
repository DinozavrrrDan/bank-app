package com.bankapp.service;

import com.bankapp.enteties.User;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserService {
    public Optional<User> getByLogin(@NonNull String login);
}
