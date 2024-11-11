package com.bankapp.repository;

import com.bankapp.enteties.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByUserId(Long userId);
}
