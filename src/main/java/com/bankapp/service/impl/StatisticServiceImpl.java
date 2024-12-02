package com.bankapp.service.impl;

import com.bankapp.repository.ExpenseRepository;
import com.bankapp.repository.IncomeRepository;
import com.bankapp.service.StatisticService;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class StatisticServiceImpl implements StatisticService {

    private final ExpenseRepository expenseRepository;
    private final IncomeRepository incomeRepository;

    public StatisticServiceImpl(ExpenseRepository expenseRepository, IncomeRepository incomeRepository) {
        this.expenseRepository = expenseRepository;
        this.incomeRepository = incomeRepository;
    }

    @Override
    public long getBalance(Long accountId) {
        AtomicReference<Long> totalExpense = new AtomicReference<>(0L);
        expenseRepository
                .findAllByUserId(accountId)
                .forEach(
                        it -> totalExpense.updateAndGet(v -> v + it.getAmount().longValue())
                );
        AtomicReference<Long> totalIncomes = new AtomicReference<>(0L);
        incomeRepository
                .findAllByUserId(accountId)
                .forEach(
                        it -> totalIncomes.updateAndGet(v -> v + it.getAmount().longValue()));
        return totalIncomes.get() - totalExpense.get();
    }
}
