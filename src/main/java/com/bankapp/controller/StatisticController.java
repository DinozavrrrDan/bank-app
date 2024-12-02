package com.bankapp.controller;

import com.bankapp.enteties.Account;
import com.bankapp.service.AccountService;
import com.bankapp.service.AuthService;
import com.bankapp.service.StatisticService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api")
public class StatisticController {

    private final StatisticService statisticService;
    private final AccountService accountService;
    private final AuthService authService;

    public StatisticController(StatisticService statisticService, AccountService accountService, AuthService authService) {
        this.statisticService = statisticService;
        this.accountService = accountService;
        this.authService = authService;
    }

    @GetMapping("statistic")
    public ResponseEntity<Long> statistic() {
        Account account = accountService.findByLogin(authService.getAuthInfo().getPrincipal().toString());
        return ResponseEntity.ok(statisticService.getBalance(account.getId()));
    }
}
