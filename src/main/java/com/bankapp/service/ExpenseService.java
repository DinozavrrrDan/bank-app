package com.bankapp.service;

import com.bankapp.enteties.Expense;
import com.bankapp.enteties.Income;

import java.util.List;

public interface ExpenseService {
    Expense saveExpense(Expense expense);
    void deleteExpense(String id);
    List<Expense> getAllExpenses();
}
