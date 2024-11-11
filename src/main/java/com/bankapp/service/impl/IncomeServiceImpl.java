package com.bankapp.service.impl;

import com.bankapp.enteties.Income;
import com.bankapp.repository.IncomeRepository;
import com.bankapp.service.IncomeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;

    public IncomeServiceImpl(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @Override
    public Income saveIncome(Income income) {
        return incomeRepository.save(income);
    }

    @Override
    public void deleteIncome(String id) {
        incomeRepository.deleteById(Long.getLong(id));
    }

    @Override
    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }
}
