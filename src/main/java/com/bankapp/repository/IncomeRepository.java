package com.bankapp.repository;

import com.bankapp.enteties.Income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findAllByUserId(Long userId);
}
