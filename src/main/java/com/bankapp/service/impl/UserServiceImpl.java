package com.bankapp.service.impl;

import com.bankapp.enteties.Role;
import com.bankapp.enteties.User;
import com.bankapp.service.UserService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final List<User> users =
            new ArrayList<>();

    public UserServiceImpl(List<User> users) {
        this.users.add(
                new User(
                        "anton",
                        "1234",
                        "",
                        "",
                        new HashSet<Role>(Role.USER.ordinal()))
        );
    }

    @Override
    public Optional<User> getByLogin(String login) {
        return users.stream()
                .filter(user -> login.equals(user.getLogin()))
                .findFirst();
    }
}
