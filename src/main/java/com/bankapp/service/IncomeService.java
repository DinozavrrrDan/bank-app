package com.bankapp.service;

import com.bankapp.enteties.Income;

import java.util.List;

public interface IncomeService {
    Income saveIncome(Income income);
    void deleteIncome(String id);
    List<Income> getAllIncomes();
}
