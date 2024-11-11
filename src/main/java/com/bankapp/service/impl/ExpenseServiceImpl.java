package com.bankapp.service.impl;

import com.bankapp.enteties.Expense;
import com.bankapp.enteties.Income;
import com.bankapp.repository.ExpenseRepository;
import com.bankapp.repository.IncomeRepository;
import com.bankapp.service.ExpenseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public void deleteExpense(String id) {
        expenseRepository.deleteById(Long.getLong(id));
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }
}
