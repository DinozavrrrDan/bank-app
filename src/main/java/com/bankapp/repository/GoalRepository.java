package com.bankapp.repository;

import com.bankapp.enteties.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findAllByUserId(Long userId);

    Goal getGoalsById(Long id);
}
