package com.bankapp.service;

import com.bankapp.enteties.Goal;
import com.bankapp.enteties.request.AddGoalRequest;

import java.util.List;

public interface GoalsService {
    List<Goal> getGoalsByUserId(Long userId);
    Goal saveGoal(Goal goal);
    void deleteGoal(String id);
    void addMoneyToGoal(AddGoalRequest addGoalRequest);
}
