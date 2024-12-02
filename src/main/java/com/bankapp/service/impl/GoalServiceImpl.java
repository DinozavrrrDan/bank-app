package com.bankapp.service.impl;

import com.bankapp.enteties.Goal;
import com.bankapp.enteties.request.AddGoalRequest;
import com.bankapp.repository.GoalRepository;
import com.bankapp.service.GoalsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalServiceImpl implements GoalsService {

    private final GoalRepository goalRepository;

    public GoalServiceImpl(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    @Override
    public List<Goal> getGoalsByUserId(Long userId) {
        return goalRepository.findAllByUserId(userId);
    }

    @Override
    public Goal saveGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    @Override
    public void deleteGoal(String id) {
        goalRepository.deleteById(Long.parseLong(id));
    }

    @Override
    public void addMoneyToGoal(AddGoalRequest addGoalRequest) {
        Goal goal = goalRepository.getGoalsById(addGoalRequest.getId());
        goal.setCurrentAmount(addGoalRequest.getCurrentAmount() + goal.getCurrentAmount());
        goalRepository.save(goal);
    }
}
