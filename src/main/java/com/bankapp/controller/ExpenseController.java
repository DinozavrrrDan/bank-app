package com.bankapp.controller;

import com.bankapp.enteties.Account;
import com.bankapp.enteties.Expense;
import com.bankapp.enteties.Income;
import com.bankapp.service.AccountService;
import com.bankapp.service.AuthService;
import com.bankapp.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final AccountService accountService;
    private final AuthService authService;

    public ExpenseController(ExpenseService expenseService, AccountService accountService, AuthService authService) {
        this.expenseService = expenseService;
        this.accountService = accountService;
        this.authService = authService;
    }

    @GetMapping("expenses")
    public ResponseEntity<List<Expense>> getExpenses() {
        Account account = accountService.findByLogin(authService.getAuthInfo().getPrincipal().toString());
        return new ResponseEntity<>(expenseService.getAllExpenses(account.getId()), HttpStatus.OK);
    }

    @PostMapping("expense")
    public ResponseEntity<Expense> saveExpense(@RequestBody Expense expense) {
        Account account = accountService.findByLogin(authService.getAuthInfo().getPrincipal().toString());
        expense.setUserId(account.getId());
        expense.setDateAdded(LocalDate.now());
        return new ResponseEntity<>(expenseService.saveExpense(expense), HttpStatus.CREATED);
    }

    @DeleteMapping("expense/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable String id) {
        expenseService.deleteExpense(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
