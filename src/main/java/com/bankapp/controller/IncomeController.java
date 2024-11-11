package com.bankapp.controller;

import com.bankapp.enteties.Account;
import com.bankapp.enteties.Income;
import com.bankapp.enteties.jwt.JwtAuthentication;
import com.bankapp.service.AccountService;
import com.bankapp.service.AuthService;
import com.bankapp.service.IncomeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api")
public class IncomeController {

    private final IncomeService incomeService;
    private final AccountService accountService;
    private final AuthService authService;

    public IncomeController(IncomeService incomeService, AccountService accountService, AuthService authService) {
        this.incomeService = incomeService;
        this.accountService = accountService;
        this.authService = authService;
    }

    @GetMapping("income")
    public ResponseEntity<List<Income>> getIncomes() {
        Account account = accountService.findByLogin(authService.getAuthInfo().getPrincipal().toString());
        return new ResponseEntity<>(incomeService.getAllIncomes(account.getId()), HttpStatus.OK);
    }

    @PostMapping("income")
    public ResponseEntity<Income> saveIncomes(@RequestBody Income income) {
        Account account = accountService.findByLogin(authService.getAuthInfo().getPrincipal().toString());
        income.setUserId(account.getId());
        income.setDateAdded(LocalDate.now());
        return new ResponseEntity<>(incomeService.saveIncome(income), HttpStatus.CREATED);
    }

    @DeleteMapping("income/{id}")
    public ResponseEntity<?> deleteIncome(@PathVariable String id) {
        incomeService.deleteIncome(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
