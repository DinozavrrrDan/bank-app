package com.bankapp.controller;

import com.bankapp.enteties.Account;
import com.bankapp.enteties.Goal;
import com.bankapp.enteties.Income;
import com.bankapp.enteties.request.AddGoalRequest;
import com.bankapp.service.AccountService;
import com.bankapp.service.AuthService;
import com.bankapp.service.GoalsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class GoalController {

    private final AccountService accountService;
    private final AuthService authService;
    private final GoalsService goalsService;

    public GoalController(AccountService accountService, AuthService authService, GoalsService goalsService) {
        this.accountService = accountService;
        this.authService = authService;
        this.goalsService = goalsService;
    }

    @GetMapping("/goals")
    public ResponseEntity<List<Goal>> getGoals() {
        Account account = accountService.findByLogin(authService.getAuthInfo().getPrincipal().toString());
        return ResponseEntity.ok(goalsService.getGoalsByUserId(account.getId()));
    }

    @PostMapping("/goal")
    public ResponseEntity<Goal> saveGoal(@RequestBody Goal goal) {
        Account account = accountService.findByLogin(authService.getAuthInfo().getPrincipal().toString());
        goal.setUserId(account.getId());
        goal.setDateAdded(LocalDate.now());
        goal.setCurrentAmount(0L);
        return new ResponseEntity<>(goalsService.saveGoal(goal), HttpStatus.CREATED);
    }

    @DeleteMapping("/goal/{id}")
    public ResponseEntity<?> deleteGoal(@PathVariable String id) {
        goalsService.deleteGoal(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/goal")
    public ResponseEntity<?> updateGoal(@RequestBody AddGoalRequest addGoalRequest) {
        goalsService.addMoneyToGoal(addGoalRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
